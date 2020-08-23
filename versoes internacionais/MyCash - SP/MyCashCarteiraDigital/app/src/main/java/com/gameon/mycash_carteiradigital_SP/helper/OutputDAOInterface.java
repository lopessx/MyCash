package com.gameon.mycash_carteiradigital_SP.helper;

import com.gameon.mycash_carteiradigital_SP.model.Output;

import java.util.List;

public interface OutputDAOInterface {

    public boolean save(Output output);
    public boolean update(Output output);
    public boolean delete(Output output);
    public List<Output> list();

}
