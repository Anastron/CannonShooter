package de.samdev.cannonshooter.util;

public final class MathUtils {

	public static int moduloSignum(float a, float b, float mod, int fallback) {
		if (a == b) return 0;

		float d_positive = ((a - b) + 2*mod) % mod;
		float d_negative = ((b - a) + 2*mod) % mod;
		
		if (d_positive > d_negative) return 1;
		else if (d_positive < d_negative) return -1; 
		else return fallback;
	}

}
