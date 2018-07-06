package pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Country implements Serializable{

    private List<States> usa=new ArrayList<>();

    public List<States> getUsa() {
        return usa;
    }

    public void setUsa(List<States> usa) {
        this.usa = usa;
    }

    @Override
    public String toString() {
        String str="{" +
                "\"usa\":[";
        StringBuilder builder=new StringBuilder(2048);
        for (States states:usa){
            builder.append(states.toString()+",");
        }
        String s = builder.toString();
        String json=s.substring(0,s.length()-1);
        return  str+json+"]}";
    }
}
