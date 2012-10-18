package com.horribile.tips.framework;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Input filter to format text by regular expression
 * @author horribile
 *
 */
public class PartialRegexInputFilter implements InputFilter {
	
	private Pattern mPattern;
	
	public PartialRegexInputFilter(String pattern){
		mPattern = Pattern.compile(pattern);
	}	

	public CharSequence filter(CharSequence source, int sourceStart, int sourceEnd,
			Spanned destination, int destinationStart, int destinationEnd) {
		
		String textToCheck = destination.subSequence(0, destinationStart).toString() +

		source.subSequence(sourceStart, sourceEnd) +
		destination.subSequence(destinationEnd, destination.length()).toString();

		Matcher matcher = mPattern.matcher(textToCheck);
		
		// Entered text does not match the pattern
		if(!matcher.matches()){
			
			// It does not match partially too
			if(!matcher.hitEnd()){
				return "";
			}
			
		}
		
		return null;
	}

}
