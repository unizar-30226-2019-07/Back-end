package selit.Location;

/**
 * Representa las ubicaciones.
 */
public class Location {
	
	/** Latitud de la ubicacion */
    private float lat;
    
    /** Longitud de la ubicacion */
    private float lng;
    
    /**
     * Constructor.
     * @param lat Latitud de la ubicacion.
     * @param lng Longitud de la ubicacion.
     */
    public Location(float lat, float lng){
    	this.lat = lat;
    	this.lng = lng;
    }
    
    /**
     * Devuelve la latitud de la ubicacion.
     * @return Latitud de la ubicacion.
     */
	public float getLat() {
		return lat;
	}

	/** 
	 * Cambia la latitud de la ubicacion a lat.
	 * @param lat Nueva latitud de la ubicacion.
	 */
	public void setLat(float lat) {
		this.lat = lat;
	}

	/**
	 * Devuelve la longitud de la ubicacion.
	 * @return Longitud de la ubicacion.
	 */
	public float getLng() {
		return lng;
	}

	/**
	 * Cambia la longitud de la ubicacion a lng.
	 * @param lng Nueva longitud de la ubicacion.
	 */
	public void setLng(float lng) {
		this.lng = lng;
	}
}
