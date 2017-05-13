package com.jetprogramming.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jetprogramming.services.SumProvider;

@Service
public class SumProviderImpl implements SumProvider {

	@Override

	public int add(String numbers) {
		String delimiter = ",|\n";
		String numbersWithoutDelimiter = numbers;
		if (numbers.startsWith("//")) {
			int delimiterIndex = numbers.indexOf("//") + 2;
			delimiter = numbers.substring(delimiterIndex, delimiterIndex + 1);
			numbersWithoutDelimiter = numbers.substring(numbers.indexOf("\n") + 1);
		}
		return add(numbersWithoutDelimiter, delimiter);
	}

	private static int add(final String numbers, final String delimiter) {
		int returnValue = 0;
		String[] numbersArray = numbers.split(delimiter);
		List<Integer> negativeNumbers = new ArrayList<Integer>();
		for (String number : numbersArray) {
			if (!number.trim().isEmpty()) {
				int numberInt = Integer.parseInt(number.trim());
				if (numberInt < 0) {
					negativeNumbers.add(numberInt);
				} else if (numberInt <= 1000) {
					returnValue += numberInt;
				}
			}
		}
		if (negativeNumbers.size() > 0) {
			throw new RuntimeException("Negatives not allowed: " + negativeNumbers.toString());
		}
		return returnValue;
	}

}
