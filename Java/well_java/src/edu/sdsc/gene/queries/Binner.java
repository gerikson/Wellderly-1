package edu.sdsc.bigonc.queries;

public class Binner {
	// this is a java version of the Jim Kent bin assignment code which was
	// written in C
	// putting in the start and end of the chromozone segment you are interested
	// in returns
	// the correct bin to search

	static int BINOFFSETOLDTOEXTENDED = 4681;
	static int BINFIRSTSHIFT = 17;
	static int BINNEXTSHIFT = 3;

	static int binFromRangeStandard(int start, int end) {

		int startBin = start, endBin = end - 1, i;
		final int binOffsets[] = { 512 + 64 + 8 + 1, 64 + 8 + 1, 8 + 1, 1, 0 };

		startBin >>= BINFIRSTSHIFT;
		endBin >>= BINFIRSTSHIFT;
		System.out.println(start + "\n");

		try {
			for (i = 0; i < binOffsets.length; ++i) {
				if (startBin == endBin) {
					System.out.println(binOffsets[i] + startBin + "\n");
					return binOffsets[i] + startBin;
				}
				startBin >>= BINNEXTSHIFT;
				endBin >>= BINNEXTSHIFT;
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	static int binFromRangeExtended(int start, int end) {

		final int binOffsetsExtended[] = { 4096 + 512 + 64 + 8 + 1,
				512 + 64 + 8 + 1, 64 + 8 + 1, 8 + 1, 1, 0 };
		int startBin = start, endBin = end - 1, i;

		try {
			startBin >>= BINFIRSTSHIFT;
			endBin >>= BINFIRSTSHIFT;
			for (i = 0; i < binOffsetsExtended.length; ++i) {
				if (startBin == endBin) {
					System.out.println(BINOFFSETOLDTOEXTENDED
							+ binOffsetsExtended[i] + startBin + "\n");
					return BINOFFSETOLDTOEXTENDED + binOffsetsExtended[i]
							+ startBin;
				}
				startBin >>= BINNEXTSHIFT;
				endBin >>= BINNEXTSHIFT;
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

}
