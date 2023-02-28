package com.example.welcometoesprit.entities;

public enum StatusComplaint {

    PENDING,   // still not checked : init state
    RESOLVED,  // either after : acknowledged + solution or disciplinary action
    REJECTED,  // dismiss complaint + explain reasons or rejection
    INVESTIGATING,  // further actions such as meetings

    // all complaints are initially Pending
    //  once a complaint is investigating , the admin manually changes the status to resolved once completed

    // for the disciplinary actions :

    //  suspension ( temporary black list )
    //  warning ( if = > 3 => set meeting )
    //  Community service
    //  Expulsion = move to black list
    //  legal action

//     for this to work , a user must have 2 additional properties :
//     integer warnings  ( init =0 )
//     enum state   ( suspended , expelled , active = init state  )
//     black list containing expelled ot suspended ( temporary for int num_days ) + access control

}
