package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class Services {

    Repo repo=new Repo();

    public void addOrder(Order o){
        if(repo.ODB.containsKey(o))return;

        repo.ODB.put(o.getId(),o);

    }

    public void addPartner(String id){
        if(repo.PDB.containsKey(id))return;

        repo.PDB.put(id,null);
    }
    public void addOrderPartnerPair(String Oid,String Pid){
        if(repo.OPDB.containsKey(Oid)){
            return;
        }
        repo.OPDB.put(Oid,Pid);
    }

    public ResponseEntity<Order> getOrderById(String Oid){
           if(!repo.ODB.containsKey(Oid))return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            try {
                Order r= repo.ODB.get(Oid);
                return new ResponseEntity<>(r,HttpStatus.CREATED);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


    }

    public DeliveryPartner PaireDelivaryPartner(String partnerId){
        DeliveryPartner NewDelivaryPartner=new DeliveryPartner(partnerId);
        int count=0;
        for(String s:repo.OPDB.keySet()){
            if(repo.OPDB.get(s).equals(partnerId)){
                count++;
            }
        }
        NewDelivaryPartner.setNumberOfOrders(count);
        repo.PDB.put(partnerId,NewDelivaryPartner);
        return NewDelivaryPartner;
    }
    public Integer OrderCount(String partnerId){

        int count=0;
        for(String OrderId : repo.OPDB.keySet()){
            if(repo.OPDB.get(OrderId).equals(partnerId)) {
                count++;
            }
        }
        return count;
    }
    public List<String> OrderBypartnerId(String partnerId){

        int count=0;
        List<String> ans= new LinkedList<>();
        for(String OrderId : repo.OPDB.keySet()){
            if(repo.OPDB.get(OrderId).equals(partnerId)) {
                ans.add(OrderId);
            }
        }
        return ans;
    }

    public List<String> getAllOrders(){
        List<String> ans=new LinkedList<>();
        for(String OrderId : repo.ODB.keySet()){

                ans.add(OrderId);
            }
        return ans;

    }

    public Integer GetCountOfUnassignedOrder(){
        Integer count=0;
        for (String fullOrder : repo.ODB.keySet()) {
            if (!repo.OPDB.containsKey(fullOrder)) {
                count++;
            }
        }
        return count;
    }
    public Integer GetCountOfOrderAfterGivingTime(String time,String partnerId){
        int count=0;
        for(String s:repo.OPDB.keySet()){
            if(repo.OPDB.get(s).equals(partnerId)){
                Order temp=repo.ODB.get(s);
                int TEMP=StringTimetoInteger(time);
                if(temp.getDeliveryTime()>TEMP){
                    count++;
                }
            }
        }
        return count;
    }
    public String getLastTime(String partnerId){
        Integer ans=0;
         for(String s:repo.OPDB.keySet()){
             if(repo.OPDB.get(s).equals(partnerId)){
                 if(ans<repo.ODB.get(s).getDeliveryTime()){
                     ans=repo.ODB.get(s).getDeliveryTime();
                 }
             }
         }
return IntegerTimetostring(ans);
    }

   public String IntegerTimetostring(Integer time){
        String ans="";
        while(time>0){
            String temp= String.valueOf(time%10);
            ans=ans+temp;
            time=time/10;
        }
        if(ans.length()==2)ans=ans+"0";

        String Ans="";
        for(int i=ans.length()-1;i>=0;i--){
            if(i==1)Ans=Ans+':';

            Ans+=ans.charAt(i);
        }

        return Ans;
    }
 public Integer StringTimetoInteger(String time){
        String ans="";
        for(int i=0;i<time.length();i++){
            if(time.charAt(i)!=':')ans+=time.charAt(i);
        }
        return Integer.parseInt(ans);
    }

    public void deletePartnerId(String partnerId){

        repo.PDB.remove(partnerId);


        Iterator<String> iterator = repo.OPDB.keySet().iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (repo.OPDB.get(s).equals(partnerId)) {
                iterator.remove();
            }
        }
    }

    public void deleteOrderId(String OrderId){
        repo.ODB.remove(OrderId);

        Iterator<String> iterator=repo.OPDB.keySet().iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.equals(OrderId)) {
                String partnerId=repo.OPDB.get(s);
                int count=repo.PDB.get(partnerId).getNumberOfOrders();
                repo.PDB.get(partnerId).setNumberOfOrders(count-1);
                iterator.remove();
            }
        }


    }

}
