package com.avanti.unit.puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.framework.templates.UnitTestCase;
import com.relevantcodes.extentreports.LogStatus;

public class MemoryReallocateTests extends UnitTestCase
{
	
	protected final static Logger logger = LogManager.getLogger(MemoryReallocateTests.class.getName());
	public static HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
	public static Integer sum = 0;
	
	public MemoryReallocateTests()
	{
		// Initialize the HashMap, could be done in a better way!
		logger.info("Initialize the HashMap, could be done in a better way!");
		hm.put(1, 0);
		hm.put(2, 5);
		hm.put(3, 10);
		hm.put(4, 0);
		hm.put(5, 11);
		hm.put(6, 14);
		hm.put(7, 13);
		hm.put(8, 4);
		hm.put(9, 11);
		hm.put(10, 8);
		hm.put(11, 8);
		hm.put(12, 7);
		hm.put(13, 1);
		hm.put(14, 4);
		hm.put(15, 12);
		hm.put(16, 11);
	}
	
	public static String allocateMemory()
	{
		// Locate the max value and it's index
		logger.info("Locate the max value and it's index ");
		int maxValueIndex = 1;
		int maxValue = hm.get(1);
		for (int i = 1; i < hm.size() + 1; i++)
		{
			if (maxValue < hm.get(i))
			{
				maxValueIndex = i;
				maxValue = hm.get(i);
			}
		}
		logger.info("Max Index: " + maxValueIndex + " Max Value: " + maxValue);
		logger.info(hm.toString());
		// Reset the max value to 0
		logger.info("Reset the max value to 0! ");
		hm.put(maxValueIndex, 0);
		logger.info(hm.toString());
		// Reallocate max value to everyone step #1
		logger.info("Reallocate max value to everyone step #1 ");
		int round = maxValue / hm.size();
		if (round > 0)
		{
			maxValue = maxValue - round * 16;
			for (int i = 1; i < hm.size() + 1; i++)
			{
				hm.put(i, hm.get(i) + round);
			}
			logger.info(hm.toString());
		}
		// Reallocate max value to everyone step #2
		logger.info("Reallocate max value to everyone step #2 ");
		for (int i = 1; i < maxValue + 1; i++)
		{
			int todo = 0;
			if ((maxValueIndex + i) < (hm.size() + 1))
				todo = maxValueIndex + i;
			else
				todo = maxValueIndex + i - hm.size();
			hm.put(todo, hm.get(todo) + 1);
		}
		logger.info(hm.toString());
		// Check the sum of reallocation integers
		logger.info("Check the sum of reallocation integers");
		for (int i = 1; i < hm.size() + 1; i++)
		{
			sum = sum + hm.get(i);
		}
		logger.info("=====================================================");
		logger.info("======================== " + sum + " ========================");
		logger.info("=====================================================");
		// Store reallocation integers to a String
		logger.info("Store reallocation integers to a String! ");
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < hm.size() + 1; i++)
		{
			sb.append(hm.get(i));
		}
		logger.info(sb);
		return sb.toString();
	}
	
	@Test(priority = 1)
	public void figureOutMemoryReallocationRoundsBeforeRepeating()
	{
		test = extent.startTest(" Puzzle: Figure out memory reallocation rounds before repeating. ");
		test.log(LogStatus.INFO, "Puzzle: Figure out memory reallocation rounds before repeating. ");
		new MemoryReallocateTests();
		ArrayList<String> allocationList = new ArrayList<>();
		int output = 0;
		while (true)
		{
			sum = 0;
			String s = allocateMemory();
			if (allocationList.contains(s))
			{
				break;
			}
			else
			{
				output++;
				allocationList.add(s);
			}
			logger.info("++++++++++++++++++++++ " + output + " ++++++++++++++++++++++");
			logger.info("++++++++++++++++++++++ " + output + " ++++++++++++++++++++++");
			logger.info("++++++++++++++++++++++ " + output + " ++++++++++++++++++++++");
			Assert.assertTrue(sum.equals(119));
		}
	}
}
