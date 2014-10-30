package edu.sdsc.extensions;

//Import all of the Vertica SDK 
import com.vertica.sdk.*;

public class TypeRulesFactory extends ScalarFunctionFactory {
	@Override
	public void getPrototype(ServerInterface srvInterface,
			ColumnTypes argTypes, ColumnTypes returnType) {
		argTypes.addVarchar();
		argTypes.addVarchar();
		returnType.addVarchar();
	}

	@Override
	public void getReturnType(ServerInterface srvInterface,
			SizedColumnTypes inputTypes, SizedColumnTypes outputTypes) {
		// Set the maximum width of the return column to the width
		// of the input column and name the output column "Tokens"
		outputTypes.addVarchar(5, // inputTypes.getColumnType(0).getStringLength(),
				"Variant Type");
	}

	// This ScalarFunction is defined as an inner class of
	// its ScalarFunctionFactory class. This gets around having
	// to have a separate source file for this public class.
	public class TypeRules extends ScalarFunction {

		@Override
		public void processBlock(ServerInterface srvInterface,
				BlockReader argReader, BlockWriter resWriter)
				throws UdfException, DestroyInvocation {
			do {
				// The input and output objects have already loaded
				// the first row, so you can start reading and writing
				// values immediately.

				String ref = argReader.getString(0);
				String alt = argReader.getString(1);

				if (!alt.contains(".")) {
					if ((ref.length() == 1 && alt.length() == 1)
							|| alt.contains(",")) {
						resWriter.setString("snp");
					} else if ((ref.length() == alt.length())
							&& (ref.length() > 1 || alt.length() > 1)) {
						resWriter.setString("sub");
					} else if (ref.length() > alt.length()) {
						resWriter.setString("del");
					} else if (ref.length() < alt.length()) {
						resWriter.setString("ins");
					} else {
						resWriter.setString("UNK");
					}
				} else if (alt.contains(".")) {
					resWriter.setString("del");
				} else {
					resWriter.setString("UNK");
				}

				// Advance the output BlocKWriter to the next row.
				resWriter.next();

				// Continue processing input rows until there are no more.
			} while (argReader.next());
		}
	}

	@Override
	public ScalarFunction createScalarFunction(ServerInterface srvInterface) {
		return new TypeRules();
	}
}