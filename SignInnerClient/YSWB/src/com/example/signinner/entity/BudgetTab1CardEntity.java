package com.example.signinner.entity;

import android.R.integer;
import android.R.string;

public class BudgetTab1CardEntity{

	private String budgetProject;
	private String budget1;
	private String budget2;
	private String implementBudget;
	private String implementRate;
	private String notImplementBudget;
	
	public String getBudgetProject() {
		return budgetProject;
	}
	public void setBudgetProject(String budgetProject) {
		this.budgetProject = budgetProject;
	}
	public String getBudget1() {
		return budget1;
	}
	public void setBudget1(String budget1) {
		this.budget1 = budget1;
	}
	public String getBudget2() {
		return budget2;
	}
	public void setBudget2(String budget2) {
		this.budget2 = budget2;
	}
	public String getImplementBudget() {
		return implementBudget;
	}
	public void setImplementBudget(String implementBudget) {
		this.implementBudget = implementBudget;
	}
	public String getImplementRate() {
		return implementRate;
	}
	public void setImplementRate(String implementRate) {
		this.implementRate = implementRate;
	}
	public String getNotImplementBudget() {
		return notImplementBudget;
	}
	public void setNotImplementBudget(String notImplementBudget) {
		this.notImplementBudget = notImplementBudget;
	}
	public BudgetTab1CardEntity(){
		
	}
	public BudgetTab1CardEntity(String budget_project,String budget1,String budget2,String implement_budget,String implement_rate,String not_implement_budget){
		super();
		this.budgetProject = budget_project;
		this.budget1 = budget1;
		this.budget2 = budget2;
		this.implementBudget = implement_budget;
		this.implementRate = implement_rate;
		this.notImplementBudget = not_implement_budget;
	
	}
}