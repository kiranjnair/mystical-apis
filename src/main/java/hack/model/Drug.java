package hack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "drugs")
public class Drug {

	@Id
	private String id;
	private String rxnormId;
	private String drugname;
	private String condition;
	private String sideeffects;
	private String foodinteraction;
	private String conditionInteraction;
	private String drugtype;
	private String healthtip1;
	private String healthtip2;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRxnormId() {
		return rxnormId;
	}
	public void setRxnormId(String rxnormId) {
		this.rxnormId = rxnormId;
	}
	public String getDrugname() {
		return drugname;
	}
	public void setDrugname(String drugname) {
		this.drugname = drugname;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getSideeffects() {
		return sideeffects;
	}
	public void setSideeffects(String sideeffects) {
		this.sideeffects = sideeffects;
	}
	public String getFoodinteraction() {
		return foodinteraction;
	}
	public void setFoodinteraction(String foodinteraction) {
		this.foodinteraction = foodinteraction;
	}
	public String getConditionInteraction() {
		return conditionInteraction;
	}
	public void setConditionInteraction(String conditionInteraction) {
		this.conditionInteraction = conditionInteraction;
	}
	public String getDrugtype() {
		return drugtype;
	}
	public void setDrugtype(String drugtype) {
		this.drugtype = drugtype;
	}
	public String getHealthtip1() {
		return healthtip1;
	}
	public void setHealthtip1(String healthtip1) {
		this.healthtip1 = healthtip1;
	}
	public String getHealthtip2() {
		return healthtip2;
	}
	public void setHealthtip2(String healthtip2) {
		this.healthtip2 = healthtip2;
	}
	@Override
	public String toString() {
		return "Drug [id=" + id + ", rxnormId=" + rxnormId + ", drugname=" + drugname + ", condition=" + condition
				+ ", sideeffects=" + sideeffects + ", foodinteraction=" + foodinteraction + ", conditionInteraction="
				+ conditionInteraction + ", drugtype=" + drugtype + ", healthtip1=" + healthtip1 + ", healthtip2="
				+ healthtip2 + "]";
	}
	
	
}
	

    