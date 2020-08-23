package com.gameon.mycash_carteiradigital.helper;

import com.gameon.mycash_carteiradigital.model.Output;

import java.util.List;

public interface OutputDAOInterface {

    boolean save(Output output);
    boolean update(Output output);
    boolean delete(Output output);
    List<Output> list();

}
