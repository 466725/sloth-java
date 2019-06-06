package com.gurock.testrail;

import java.util.HashMap;
import java.util.Map;
import com.gurock.testrail.enums.TestContextEnum;

/**
 * This class holds certain thread local variables
 */
public class LocalEntities
{
	
	private static LocalEntities instance = null;
	private InheritableThreadLocal<Map<Enum<TestContextEnum>, Object>> entities = new InheritableThreadLocal<Map<Enum<TestContextEnum>, Object>>();
	
	private LocalEntities()
	{
	}
	
	/**
	 * Use singleton to make sure only one instance globally
	 * 
	 * @return
	 */
	public static LocalEntities getInstance()
	{
		if (instance == null)
		{
			synchronized (LocalEntities.class)
			{
				if (instance == null)
					instance = new LocalEntities();
			}
		}
		return instance;
	}
	
	/**
	 * return entity object
	 * 
	 * @return
	 */
	public Map<Enum<TestContextEnum>, Object> getEntities()
	{
		return entities.get();
	}
	
	/**
	 * set entity object
	 * 
	 * @param t
	 */
	public void setEntities(Map<Enum<TestContextEnum>, Object> t)
	{
		this.entities.set(t);
	}
	
	/**
	 * clean up entity holder
	 */
	public void clean()
	{
		this.entities.remove();
	}
	
	/**
	 * Get the entity for a certain type. If not set, then return null;
	 * 
	 * @param type
	 * @return
	 */
	public Object getEntity(TestContextEnum type)
	{
		Map<Enum<TestContextEnum>, Object> entities = getEntities();
		if (entities == null)
			return null;
		else
			return this.entities.get().get(type);
	}
	
	/**
	 * Set an entity to the test context for the current test thread
	 * 
	 * @param type
	 * @param value
	 */
	public void setEntity(TestContextEnum type, Object value)
	{
		Map<Enum<TestContextEnum>, Object> entities = getEntities();
		if (entities == null)
		{
			entities = new HashMap<Enum<TestContextEnum>, Object>();
		}
		entities.put(type, value);
		setEntities(entities);
	}
}
