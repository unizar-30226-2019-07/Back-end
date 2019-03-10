package main.java.chat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * Definicion de un dato de tipo Chat
 */
@Entity
public class Chat {
	
    @Id // Autogeneracion de IDs
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
}

