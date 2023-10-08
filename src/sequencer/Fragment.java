/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

import java.util.ArrayList;
import java.util.List;

public class Fragment {
	private String nucleotides;

	/**
	 * Creates a new Fragment based upon a String representing a sequence of
	 * nucleotides, containing only the uppercase characters G, C, A and T.
	 * 
	 * @param nucleotides
	 * @throws IllegalArgumentException if invalid characters are in the sequence of
	 *                                  nucleotides
	 */
	public Fragment(String nucleotides) throws IllegalArgumentException {
		this.nucleotides = nucleotides;
		if (!nucleotides.matches("[GCAT]+")) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns the length of this fragment.
	 * 
	 * @return the length of this fragment
	 */
	public int length() {
		return this.nucleotides.length();
	}

	/**
	 * Returns a String representation of this fragment, exactly as was passed to
	 * the constructor.
	 * 
	 * @return a String representation of this fragment
	 */
	@Override
	public String toString() {
		return this.nucleotides;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fragment other = (Fragment) obj;
		if (nucleotides == null) {
			if (other.nucleotides != null)
				return false;
		} else if (!nucleotides.equals(other.nucleotides))
			return false;
		return true;
	}

	/**
	 * Returns the number of nucleotides of overlap between the end of this fragment
	 * and the start of another fragment, f.
	 * 
	 * The largest overlap is found, for example, CAA and AAG have an overlap of 2,
	 * not 1.
	 * 
	 * @param f the other fragment
	 * @return the number of nucleotides of overlap
	 */
	public int calculateOverlap(Fragment f) {
		String originalnucleotide = this.nucleotides;
		String nextnucleotide = f.nucleotides;

		int overlaptotal = 0;
		int lengthoriginal = originalnucleotide.length();
		int lengthnext = nextnucleotide.length();

		int shortestfragmentlength = Math.min(lengthoriginal, lengthnext);

		for (int i = 1; i <= shortestfragmentlength; i++) {
			String endoriginalnucleotide = originalnucleotide.substring(lengthoriginal - i);

			if (nextnucleotide.startsWith(endoriginalnucleotide)) {
				overlaptotal = i;
			}
		}

		return overlaptotal;
	}

	/**
	 * Returns a new fragment based upon merging this fragment with another fragment
	 * f.
	 * 
	 * This fragment will be on the left, the other fragment will be on the right;
	 * the fragments will be overlapped as much as possible during the merge.
	 * 
	 * @param f the other fragment
	 * @return a new fragment based upon merging this fragment with another fragment
	 */
	public Fragment mergedWith(Fragment f) {
		String concatenatedString = new String();
		String originalnucleotide = this.nucleotides;
		String nextnucleotide = f.nucleotides;
		if (this.calculateOverlap(f) == 0 && f.calculateOverlap(this) == 0) {
			concatenatedString = this.nucleotides + f;
		}

		int howMuchOverlap = this.calculateOverlap(f);
		String newNextString = nextnucleotide.substring(howMuchOverlap);
		concatenatedString = originalnucleotide + newNextString;
		Fragment Fragmenttoreturn = new Fragment(concatenatedString);
		return Fragmenttoreturn;
	}

}
