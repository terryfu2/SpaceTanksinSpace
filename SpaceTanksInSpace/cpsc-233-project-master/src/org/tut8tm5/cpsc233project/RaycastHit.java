package org.tut8tm5.cpsc233project;

public class RaycastHit {
    // exact point it hit
    public final Vector2f point;

    // normal to use for reflection
    public final Vector2f normal;

    // type of wall that was hit
    public final byte wallType;

    // position on the grid of the wall that it hit
    public final Vector2f wallPosition;

    public RaycastHit(Vector2f pt, Vector2f nm, byte wtype, Vector2f wpos) {
        point = pt;
        normal = nm;
        wallType = wtype;
        wallPosition = wpos;
    }
}

//test