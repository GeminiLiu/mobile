package daiwei.mobile.util.TempletUtil.TMPModel;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author qicaihua
 *  <action id='803040001' code='NEXT' text='新建派发' radio='1' photo='1'>
    <field code='tmp_PS_CreateDesc' type='TEXTAREA' row='4'>新建派发说明</field>
    <field code='tmp_PS_Create_ToRoles' type='TREE' dic='dic_2'>代维管理员</field>
   	</action>
 *
 */
public class ActionButton implements Serializable  {
	private static final long serialVersionUID = -7060210544600424481L;
	private String id;
	private String code;
	private String name;
	private String radio;
	private String photo;
	private String createaction;
	public String getCreateaction() {
		return createaction;
	}
	public void setCreateaction(String createaction) {
		this.createaction = createaction;
	}
	private List<Field> field;
	
	private String cityID;//地市
	private String specialtyID;//专业
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public List<Field> getField() {
		return field;
	}
	public void setField(List<Field> field) {
		this.field = field;
	}
	public String getCityID() {
		return cityID;
	}
	public void setCityID(String cityID) {
		this.cityID = cityID;
	}
	public String getSpecialtyID() {
		return specialtyID;
	}
	public void setSpecialtyID(String specialtyID) {
		this.specialtyID = specialtyID;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}