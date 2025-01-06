package com.example.demo.service;

import com.example.demo.entity.GameUser;

import java.util.List;

public interface UserDAO {
    public List<GameUser> getAllUsers();
    public GameUser getUserById(int id);
    public void addUser(GameUser gameUser);
    public void updateUser(GameUser gameUser);
    public void deleteUser(int id);
}