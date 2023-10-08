/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assembler {

	private ArrayList fragments;

	/**
	 * Creates a new Assembler containing a list of fragments.
	 * 
	 * The list is copied into this assembler so that the original list will not be
	 * modified by the actions of this assembler.
	 * 
	 * @param fragments
	 */
	public Assembler(List<Fragment> fragments) {
		this.fragments = new ArrayList(fragments);
	}

	/**
	 * Returns the current list of fragments this assembler contains.
	 * 
	 * @return the current list of fragments
	 */
	public List<Fragment> getFragments() {
		return this.fragments;
	}

	/**
	 * Attempts to perform a single assembly, returning true if an assembly was
	 * performed.
	 * 
	 * This method chooses the best assembly possible, that is, it merges the two
	 * fragments with the largest overlap, breaking ties between merged fragments by
	 * choosing the shorter merged fragment.
	 * 
	 * Merges must have an overlap of at least 1.
	 * 
	 * After merging two fragments into a new fragment, the new fragment is inserted
	 * into the list of fragments in this assembler, and the two original fragments
	 * are removed from the list.
	 * 
	 * @return true iff an assembly was performed
	 */
	public boolean assembleOnce() {
		int maxOverlap = 0;
		int firstvalueinoverlap = -1;
		int nextvalueinoverlap = -1;

		for (int k = 0; k < this.fragments.size(); k++) {
			Fragment currentFragment = (Fragment) this.fragments.get(k);
			for (int i = 0; i <= this.fragments.size() - 1; i++) {
				Fragment nextFragment = (Fragment) this.fragments.get(i);
				if (i != k) {
					int overlap = currentFragment.calculateOverlap(nextFragment);

					if (overlap >= 1 && overlap > maxOverlap) {
						maxOverlap = overlap;
						firstvalueinoverlap = k;
						nextvalueinoverlap = i;
					}
				}
			}
		}
		if (maxOverlap >= 1) {
			Fragment tester = ((Fragment) this.fragments.get(firstvalueinoverlap))
					.mergedWith((Fragment) this.fragments.get(nextvalueinoverlap));
			Fragment firstvalue = (Fragment) this.fragments.get(firstvalueinoverlap);
			Fragment secondvalue = (Fragment) this.fragments.get(nextvalueinoverlap);

			this.fragments.remove(firstvalue);
			this.fragments.remove(secondvalue);
			this.fragments.add(tester);

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Repeatedly assembles fragments until no more assembly can occur.
	 */
	public void assembleAll() {
		while (this.fragments.size() > 1) {
			assembleOnce();
		}
	}

}
