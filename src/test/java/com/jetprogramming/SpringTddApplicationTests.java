package com.jetprogramming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jetprogramming.services.SumProvider;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTddApplicationTests {

	@Autowired
	private SumProvider sumProvider;

	@Test
	public final void when2NumbersAreUsedThenNoExceptionIsThrown() {
		sumProvider.add("1,2");
		assertTrue(true);
	}

	@Test(expected = RuntimeException.class)
	public final void whenNonNumberIsUsedThenExceptionIsThrown() {
		sumProvider.add("1,X");
	}

	@Test
	public final void whenEmptyStringIsUsedThenReturnValueIs0() {
		assertEquals(0, sumProvider.add(""));
	}

	@Test
	public final void whenOneNumberIsUsedThenReturnValueIsThatSameNumber() {
		assertEquals(3, sumProvider.add("3"));
	}

	@Test
	public final void whenTwoNumbersAreUsedThenReturnValueIsTheirSum() {
		assertEquals(3 + 6, sumProvider.add("3,6"));
	}

	@Test
	public final void whenAnyNumberOfNumbersIsUsedThenReturnValuesAreTheirSums() {
		assertEquals(3 + 6 + 15 + 18 + 46 + 33, sumProvider.add("3,6,15,18,46,33"));
	}

	@Test
	public final void whenNewLineIsUsedBetweenNumbersThenReturnValuesAreTheirSums() {
		assertEquals(3 + 6 + 15, sumProvider.add("3,6\n15"));
	}

	@Test
	public final void whenDelimitedIsSpecifiedThenItIsUsedToSeparateNumbers() {
		assertEquals(3 + 6 + 15, sumProvider.add("//;\n3;6;15"));
	}

	@Test(expected = RuntimeException.class)
	public final void whenNegativeNumberIsUsedThenRuntimeExceptionIsThrown() {
		sumProvider.add("3,-6,15,18,46,33");
	}

	@Test
	public final void whenNegativeNumbersAreUsedThenRuntimeExceptionIsThrown() {
		RuntimeException exception = null;
		try {
			sumProvider.add("3,-6,15,-18,46,33");
		} catch (RuntimeException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals("Negatives not allowed: [-6, -18]", exception.getMessage());
	}

	@Test
	public final void whenOneOrMoreNumbersAreGreaterThan1000IsUsedThenItIsNotIncludedInSum() {
		assertEquals(3 + 1000 + 6, sumProvider.add("3,1000,1001,6,1234"));
	}

}
