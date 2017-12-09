package com.advpro2.basone.kongmalaew;

/**
 * Created by kanazang on 7/19/2017.
 */

public class Singleton {
    private static Singleton ourInstance;

    public static Singleton getInstance() {
        if (ourInstance == null){ //if there is no instance available... create new one
            ourInstance = new Singleton();
        }

        return ourInstance;
    }





    private Singleton() {
    }

    private String firstName;
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String lastName;
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {this.lastName = lastName;
    }

    private String profilePic;
    public String getProfilePic() {
        return this.profilePic;
    }

    public void setProfilePic(String profliePic) {this.profilePic = profliePic;
    }
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}






