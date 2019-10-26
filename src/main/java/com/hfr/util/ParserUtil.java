package com.hfr.util;

public class ParserUtil {
	
	public static int parseColor(String input) {
		
		input = input.toLowerCase();
		
		if(input.length() == 6) {
			
			int num = 0;
			
			for(int i = 0; i < 6; i++) {
				
				int exp = 5 - i;
				int val = 0;
				
				switch(input.charAt(i)) {
				case '0': break;
				case '1': val = 1; break;
				case '2': val = 2; break;
				case '3': val = 3; break;
				case '4': val = 4; break;
				case '5': val = 5; break;
				case '6': val = 6; break;
				case '7': val = 7; break;
				case '8': val = 8; break;
				case '9': val = 9; break;
				case 'a': val = 10; break;
				case 'b': val = 11; break;
				case 'c': val = 12; break;
				case 'd': val = 13; break;
				case 'e': val = 14; break;
				case 'f': val = 15; break;
				default: return -1;
				}
				
				num += val * Math.pow(16, exp);
			}
			
			return num;
		}
		
		return -1;
	}

}
