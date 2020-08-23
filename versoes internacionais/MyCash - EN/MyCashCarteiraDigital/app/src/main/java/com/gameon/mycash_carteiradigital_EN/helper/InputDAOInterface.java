package com.gameon.mycash_carteiradigital_EN.helper;

import com.gameon.mycash_carteiradigital_EN.model.Input;

import java.util.List;

public interface InputDAOInterface {

    public boolean save(Input input);
    public boolean update(Input input);
    public boolean delete(Input input);
    public List<Input> list();

}
