package br.com.ddlrs.dla.rankfood.model;

import br.com.ddlrs.dla.rankfood.controller.User;

public class RelationshipPointer {
    public final int log;
    public final User user;
    public final int relationshipId;

    public RelationshipPointer(int log, User user, int relationshipId){
        this.log = log;
        this.user = user;
        this.relationshipId = relationshipId;
    }
}
