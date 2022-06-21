package br.com.ddlrs.dla.rankfood.model;

import br.com.ddlrs.dla.rankfood.controller.User;

public class UserPointer {
    public final int id;
    public final User user;

    public UserPointer(int id, User user){
        this.id = id;
        this.user = user;
    }

}
