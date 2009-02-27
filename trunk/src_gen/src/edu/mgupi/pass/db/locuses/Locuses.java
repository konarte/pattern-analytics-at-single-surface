/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Raidan Flk
 * License Type: Evaluation
 */
package edu.mgupi.pass.db.locuses;

import org.orm.*;
import java.io.Serializable;
/**
 * ������� ����������
 */
public class Locuses implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(Locuses.class);
	public Locuses() {
	}
	
	public boolean save() throws PersistentException {
		try {
			edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().saveObject(this);
			return true;
		}
		catch (Exception e) {
			_logger.error("save()", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete() throws PersistentException {
		try {
			edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().deleteObject(this);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete()", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh() throws PersistentException {
		try {
			edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession().refresh(this);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh()", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict() throws PersistentException {
		try {
			edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession().evict(this);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict()", e);
			throw new PersistentException(e);
		}
	}
	
	private int idLocus;
	
	private String name;
	
	private byte[] thumbImage;
	
	private int[] histogram;
	
	private byte[] filteredImage;
	
	private edu.mgupi.pass.db.locuses.LModules module;
	
	private edu.mgupi.pass.db.surfaces.Surfaces surface;
	
	private edu.mgupi.pass.db.defects.Defects defect;
	
	private edu.mgupi.pass.db.locuses.LocusSources locusSource;
	
	private edu.mgupi.pass.db.sensors.Sensors sensor;
	
	private java.util.Set<edu.mgupi.pass.db.locuses.LocusModuleParams> params = new java.util.HashSet<edu.mgupi.pass.db.locuses.LocusModuleParams>();
	
	private java.util.List<edu.mgupi.pass.db.locuses.LocusFilters> filters = new java.util.ArrayList<edu.mgupi.pass.db.locuses.LocusFilters>();
	
	private void setIdLocus(int value) {
		this.idLocus = value;
	}
	
	public int getIdLocus() {
		return idLocus;
	}
	
	public int getORMID() {
		return getIdLocus();
	}
	
	/**
	 * �������� ��������� (�������� �������������). ��-��������� ���������� �� �������� �����
	 */
	public void setName(String value) {
		this.name = value;
	}
	
	/**
	 * �������� ��������� (�������� �������������). ��-��������� ���������� �� �������� �����
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * ����������� �������������, ������� ��� ��������������� �� ������������ �������, 
	 * � ���� ���� ��������� ��� ��������� �������, 
	 * ����� ���� �� ���� �� ����� ������� ������� (��� ����������� � ����������) 
	 * � ����������� � ���� �����������. 
	 * 
	 * ������ ����������� ������ ���������� 256x256 �������� � ��������� � �������, 
	 * ������� ��� �������� � ����������.
	 */
	public void setThumbImage(byte[] value) {
		this.thumbImage = value;
	}
	
	/**
	 * ����������� �������������, ������� ��� ��������������� �� ������������ �������, 
	 * � ���� ���� ��������� ��� ��������� �������, 
	 * ����� ���� �� ���� �� ����� ������� ������� (��� ����������� � ����������) 
	 * � ����������� � ���� �����������. 
	 * 
	 * ������ ����������� ������ ���������� 256x256 �������� � ��������� � �������, 
	 * ������� ��� �������� � ����������.
	 */
	public byte[] getThumbImage() {
		return thumbImage;
	}
	
	/**
	 * ����������� ����������� � ������������ ���������.
	 * 
	 * ������ �������� -- ������� (�.�. ��������� ������).
	 * ������ ����������� ������� �� ������� �����������.
	 */
	public void setHistogram(int[] value) {
		this.histogram = value;
	}
	
	/**
	 * ����������� ����������� � ������������ ���������.
	 * 
	 * ������ �������� -- ������� (�.�. ��������� ������).
	 * ������ ����������� ������� �� ������� �����������.
	 */
	public int[] getHistogram() {
		return histogram;
	}
	
	/**
	 * ������� ����������� � ��� ������������������ � � ������������ ���������. 
	 * ������ ����������� 1024x1024 ��������.
	 * ������ �������� -- PNG.
	 * 
	 * ��� ������ ��� �����-��������� ��������� (��������� ������ ������ 
	 * ����� ������� ���� ����������� ������ � ��������� �������).
	 */
	public void setFilteredImage(byte[] value) {
		this.filteredImage = value;
	}
	
	/**
	 * ������� ����������� � ��� ������������������ � � ������������ ���������. 
	 * ������ ����������� 1024x1024 ��������.
	 * ������ �������� -- PNG.
	 * 
	 * ��� ������ ��� �����-��������� ��������� (��������� ������ ������ 
	 * ����� ������� ���� ����������� ������ � ��������� �������).
	 */
	public byte[] getFilteredImage() {
		return filteredImage;
	}
	
	public void setModule(edu.mgupi.pass.db.locuses.LModules value) {
		this.module = value;
	}
	
	public edu.mgupi.pass.db.locuses.LModules getModule() {
		return module;
	}
	
	public void setSurface(edu.mgupi.pass.db.surfaces.Surfaces value) {
		this.surface = value;
	}
	
	public edu.mgupi.pass.db.surfaces.Surfaces getSurface() {
		return surface;
	}
	
	public void setDefect(edu.mgupi.pass.db.defects.Defects value) {
		this.defect = value;
	}
	
	public edu.mgupi.pass.db.defects.Defects getDefect() {
		return defect;
	}
	
	public void setLocusSource(edu.mgupi.pass.db.locuses.LocusSources value) {
		this.locusSource = value;
	}
	
	public edu.mgupi.pass.db.locuses.LocusSources getLocusSource() {
		return locusSource;
	}
	
	public void setParams(java.util.Set<edu.mgupi.pass.db.locuses.LocusModuleParams> value) {
		this.params = value;
	}
	
	public java.util.Set<edu.mgupi.pass.db.locuses.LocusModuleParams> getParams() {
		return params;
	}
	
	
	public void setFilters(java.util.List<edu.mgupi.pass.db.locuses.LocusFilters> value) {
		this.filters = value;
	}
	
	public java.util.List<edu.mgupi.pass.db.locuses.LocusFilters> getFilters() {
		return filters;
	}
	
	
	public void setSensor(edu.mgupi.pass.db.sensors.Sensors value) {
		this.sensor = value;
	}
	
	public edu.mgupi.pass.db.sensors.Sensors getSensor() {
		return sensor;
	}
	
	private boolean processed;
	
	public boolean getProcessed() {
		return processed;
	}
	
	public void setProcessed(boolean aProcessed) {
		processed = aProcessed;
	}
	
	public String toString() {
		return String.valueOf(getIdLocus());
	}
	
}
