package edu.sdsc.extensions;

// Import all of the Vertica SDK 
import com.vertica.sdk.*;

public class PosRulesFactory extends ScalarFunctionFactory {
	@Override
	public void getPrototype(ServerInterface srvInterface,
			ColumnTypes argTypes, ColumnTypes returnType) {
		argTypes.addVarchar();
		argTypes.addVarchar();
		argTypes.addVarchar();
		returnType.addInt();
	}

	/*
	 * @Override public void getReturnType(ServerInterface srvInterface,
	 * SizedColumnTypes inputTypes, SizedColumnTypes outputTypes) { // Set the
	 * maximum width of the return column to the width // of the input column
	 * and name the output column "Tokens"
	 * outputTypes.addVarchar(inputTypes.getColumnType(0).getStringLength(),
	 * "Reference"); }
	 */

	// This ScalarFunction is defined as an inner class of
	// its ScalarFunctionFactory class. This gets around having
	// to have a separate source file for this public class.
	public class PosRules extends ScalarFunction {

		int mod_pos;
		String mod_alt;
		String mod_ref;

		@Override
		public void processBlock(ServerInterface srvInterface,
				BlockReader argReader, BlockWriter resWriter)
				throws UdfException, DestroyInvocation {
			do {
				// The input and output objects have already loaded
				// the first row, so you can start reading and writing
				// values immediately.

				String type = argReader.getString(0);
				String ref = argReader.getString(1);
				String alt = argReader.getString(2);
				mod_ref = "";
				mod_alt = "";
				mod_pos = -1; //transform to 0 based index

				if (type.equals("del")) {
					if (alt.contains(".")) {
						mod_ref = "-";
					} else {
						for (int i = 0; i < ref.length(); i++) {
							if (i != 0) {
								mod_ref += ref.charAt(i);
							}
						}
					}
					mod_alt = "-";
					mod_pos += 1;
				} else if (type.equals("snp")) {
					mod_ref = ref;
					mod_alt = alt;
				} else if (type.equals("ins")) {
					for (int i = 0; i < alt.length(); i++) {
						if (i != 0) {
							mod_alt += alt.charAt(i);
							mod_ref = "-";
						}
						mod_pos = 0;
					}
				} else if (type.equals("sub")) {
					boolean no_change = true;
					for (int i = 0; i < alt.length(); i++) {
						if (no_change) {
							if (ref.charAt(i) != alt.charAt(i)) {
								mod_ref += ref.charAt(i);
								mod_alt += alt.charAt(i);
								mod_pos += 1;
								no_change = false;

							} else {
								mod_ref += "";
								mod_alt += "";
							}
						}

						no_change = true;
						for (int j = alt.length() - 1; j > 0; j--) {
							if (no_change) {
								if (ref.charAt(j) != alt.charAt(j)) {
									mod_ref += ref.charAt(j);
									mod_alt += alt.charAt(j);
									no_change = false;

								} else {
									mod_ref += "";
									mod_alt += "";
								}
							}
						}
					}
				}

				resWriter.setLong(mod_pos);
				// Advance the output BlocKWriter to the next row.
				resWriter.next();

				// Continue processing input rows until there are no more.
			} while (argReader.next());
		}
	}

	@Override
	public ScalarFunction createScalarFunction(ServerInterface srvInterface) {
		return new PosRules();
	}
}