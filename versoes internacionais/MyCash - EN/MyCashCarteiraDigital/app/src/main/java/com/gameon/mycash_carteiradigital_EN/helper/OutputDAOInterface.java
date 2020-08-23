package com.gameon.mycash_carteiradigital_EN.helper;

import com.gameon.mycash_carteiradigital_EN.model.Output;

import java.util.List;

public interface OutputDAOInterface {

    public boolean save(Output output);
    public boolean update(Output output);
    public boolean delete(Output output);
    public List<Output> list();

}
