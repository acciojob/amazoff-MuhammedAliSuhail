package com.driver;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class Repo {
public HashMap<String,Order> ODB=new HashMap<>();
public HashMap<String,DeliveryPartner> PDB=new HashMap<>();

public HashMap<String,String> OPDB=new HashMap<>();

}
