package com.atbm.models;

public class ProductState {
    private Long stateId;
    private String stateName;

    public ProductState() {
    }

    public ProductState(Long stateId, String stateName) {
        this.stateId = stateId;
        this.stateName = stateName;
    }

    public Long getStateId() {
        return stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
