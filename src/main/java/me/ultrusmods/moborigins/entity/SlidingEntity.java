package me.ultrusmods.moborigins.entity;

/*
    This is used as helper methods for living entity with the Sliding Power
    Packet will be sent C2S from client that begins/stops sliding.
    This will sync the players sliding state between the client and server.
 */
public interface SlidingEntity {
    void setClimbing();
    boolean isClimbing();
}
