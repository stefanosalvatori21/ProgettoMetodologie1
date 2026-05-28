package it.unicam.cs.mpgc.rpg126115.model.transformation;

public interface Transformable {
    boolean canTransformPartial();
    void activatePartial();
    TransformationState getTransformationState();
}
