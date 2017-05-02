package com.iteedu.crypto.utils;

public class Main {

	public static final String rsaPrivate = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAK1KcqvSoIDMOh/f6gs9tbKcgBrkm7HgqydhMvVMHjESsTdg4umSsUqG8Wd4iYH57fmC1HVa+GPB5q+NIanOPW0Cqtj+jRk+j3Tn8viZbeyVtmtrtTeT0sV1DOUF+eQojYOvqlIEliFihVY62wpI6/zRzeM+Aj/Z2+tEVcOBWgEJAgMBAAECgYA1px436r+9AP5iDg7FqjaOdXwIV42JFlmHmMWRQx757KKn1byLutpfi8ZEVgKlFn/Cx+oF58OOB2PoKJcqnutPSTPmVeFSjEyqMJ4AXXWVb+MN+/J9lrZFhOkpnjvnLjcTPpMJZ2e98sp5tBna65nATmX4yJBjKM3paYjDu6vIzQJBAOQfo+PhXx7ESRNVRLnyyYHyoI2YAz9U2I2G1P1O/n9FJa2k2a8R3klLcBMj9gmjD1B/42MSPdbp7qcPruK4KksCQQDCd3studbk+SBvMKvxrK7cf6cZm3AKgsrc6tLJ6ChDDGu2uRPtSlxgCW7fQ7oPKEcRN4CGAGZsWsnqeLG9O617AkAmgizRT/8Vm37Wc6NpXFGlzQZLQKjzrOftZCBaLlaJt2t314cjpXmHl+NwZ7alw8/W/++rjq2/tLejneMgxIPnAkBAFZm9JtnfWQ3MNpDjMpHpThPmB2gzvohVpvjR2rSx67zvWSxs7S1l5JXp82q0JgTNcqni/uzB1mVl5GJGRw1TAkBeqFqSsUBPI6uIB48WagT1EqCvFXxH751D9L5bwTeQBUx0YZE+6OMdTh4oNQxp1LiVNkVJelCM79E9TAO9FgUN";

	public static final String rsaPub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCtSnKr0qCAzDof3+oLPbWynIAa5Jux4KsnYTL1TB4xErE3YOLpkrFKhvFneImB+e35gtR1WvhjweavjSGpzj1tAqrY/o0ZPo905/L4mW3slbZra7U3k9LFdQzlBfnkKI2Dr6pSBJYhYoVWOtsKSOv80c3jPgI/2dvrRFXDgVoBCQIDAQAB";

	
	public static void main(String[] args) {
		// -----------º”√‹π˝≥Ã------------
		// aesÀÊª˙√‹‘ø
		String aesKey = EnUtils.getAesKey(10);
		System.out.println(aesKey);
		// ∂‘aesÀÊª˙√‹‘øº”√‹
		String aesRsaKey = EnUtils.rsaEnc(aesKey, rsaPub);
		System.out.println(aesRsaKey);
		// AESº”√‹t
		String content = "≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹≤‚ ‘º”√‹";
		String strEn = EnUtils.aesEnc(content, aesKey);
		System.out.println(strEn);
		// --------------------Ω‚√‹π˝≥Ã------------
		// Ω‚√‹≥ˆAESÀÊª˙√‹‘ø
		String secretKey = DeUtils.rsaDec(aesRsaKey, rsaPrivate);
		System.out.println(secretKey);
		// AESΩ‚√‹
		String strDe = DeUtils.aesDec(strEn, secretKey);
		System.out.println(strDe);
	}
}
