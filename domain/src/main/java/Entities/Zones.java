package Entities;

public class Zones
{
	private String zone;
	private String arrivalZone;
	
	public String getZone()
	{
		return zone;
	}
	
	public void setZone(String zone)
	{
		this.zone = zone;
	}	

	public String getArrivalZone()
	{
		return arrivalZone;
	}

	public void setArrivalZone( String arrivalZone )
	{
		this.arrivalZone = arrivalZone;
	}
	
	public String toString()
	{
		return
			" departure zone: " + zone;
	}
}
