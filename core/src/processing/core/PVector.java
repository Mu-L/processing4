/* -*- mode: java; c-basic-offset: 2; indent-tabs-mode: nil -*- */

/*
  Part of the Processing project - http://processing.org

  Copyright (c) 2012-17 The Processing Foundation
  Copyright (c) 2008-12 Ben Fry and Casey Reas
  Copyright (c) 2008 Dan Shiffman

  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License version 2.1 as published by the Free Software Foundation.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General
  Public License along with this library; if not, write to the
  Free Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA  02111-1307  USA
 */

package processing.core;

import java.io.Serializable;


/**
 *
 * A class to describe a two or three dimensional vector, specifically a
 * Euclidean (also known as geometric) vector. A vector is an entity that has
 * both magnitude and direction. The datatype, however, stores the components of
 * the vector (x,y for 2D, and x,y,z for 3D). The magnitude and direction can be
 * accessed via the methods <b>mag()</b> and <b>heading()</b>.<br />
 * <br />
 * In many of the Processing examples, you will see <b>PVector</b> used to
 * describe a position, velocity, or acceleration. For example, if you consider
 * a rectangle moving across the screen, at any given instant it has a position
 * (a vector that points from the origin to its location), a velocity (the rate
 * at which the object's position changes per time unit, expressed as a vector),
 * and acceleration (the rate at which the object's velocity changes per time
 * unit, expressed as a vector). Since vectors represent groupings of values, we
 * cannot simply use traditional addition/multiplication/etc. Instead, we'll
 * need to do some "vector" math, which is made easy by the methods inside the
 * <b>PVector</b> class.
 *
 * <h3>Advanced</h3>
 * A class to describe a two or three dimensional vector.
 * <p>
 * The result of all functions are applied to the vector itself, with the
 * exception of cross(), which returns a new PVector (or writes to a specified
 * 'target' PVector). That is, add() will add the contents of one vector to this
 * one. Using add() with additional parameters allows you to put the result into
 * a new PVector. Functions that act on multiple vectors also include static
 * versions. Because creating new objects can be computationally expensive, most
 * functions include an optional 'target' PVector, so that a new PVector object
 * is not created with each operation.
 * <p>
 * Initially based on the Vector3D class by
 * <a href="http://www.shiffman.net">Dan Shiffman</a>.
 *
 * @webref math
 * @webBrief A class to describe a two or three dimensional vector
 */
public class PVector implements Serializable {
  /**
   *
   * The x component of the vector. This field (variable) can be used to both
   * get and set the value (see above example.)
   *
   *
   * @webref pvector:field
   * @usage web_application
   * @webBrief  The x component of the vector
   */
  public float x;

  /**
   *
   * The y component of the vector. This field (variable) can be used to both
   * get and set the value (see above example.)
   *
   *
   * @webref pvector:field
   * @usage web_application
   * @webBrief  The y component of the vector
   */
  public float y;

  /**
   *
   * The z component of the vector. This field (variable) can be used to both
   * get and set the value (see above example.)
   *
   *
   * @webref pvector:field
   * @usage web_application
   * @webBrief  The z component of the vector
   */
  public float z;

  /** Array so that this can be temporarily used in an array context */
  transient protected float[] array;


  /**
   * Constructor for an empty vector: x, y, and z are set to 0.
   */
  public PVector() {
  }


  /**
   * Constructor for a 3D vector.
   *
   * @param  x the x coordinate.
   * @param  y the y coordinate.
   * @param  z the z coordinate.
   */
  public PVector(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }


  /**
   * Constructor for a 2D vector: z coordinate is set to 0.
   */
  public PVector(float x, float y) {
    this.x = x;
    this.y = y;
  }


  /**
   *
   * Sets the x, y, and z component of the vector using two or three separate
   * variables, the data from a <b>PVector</b>, or the values from a float array.
   *
   *
   * @webref pvector:method
   * @param x the x component of the vector
   * @param y the y component of the vector
   * @param z the z component of the vector
   * @webBrief  Set the components of the vector
   */
  public PVector set(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
    return this;
  }


  /**
   * @param x the x component of the vector
   * @param y the y component of the vector
   */
  public PVector set(float x, float y) {
    this.x = x;
    this.y = y;
    this.z = 0;
    return this;
  }


  /**
   * @param v any variable of type PVector
   */
  public PVector set(PVector v) {
    x = v.x;
    y = v.y;
    z = v.z;
    return this;
  }


  /**
   * Set the x, y (and maybe z) coordinates using a float[] array as the source.
   * @param source array to copy from
   */
  public PVector set(float[] source) {
    if (source.length >= 2) {
      x = source[0];
      y = source[1];
    }
    if (source.length >= 3) {
      z = source[2];
    } else {
      z = 0;
    }
    return this;
  }


  /**
   *
   * Returns a new 2D unit vector with a random direction. If you pass in
   * <b>this</b> as an argument, it will use the PApplet's random number
   * generator.
   *
   * @webref pvector:method
   * @usage web_application
   * @return the random PVector
   * @webBrief Make a new 2D unit vector with a random direction
   * @see PVector#random3D()
   */
  static public PVector random2D() {
    return random2D(null, null);
  }


  /**
   * Make a new 2D unit vector with a random direction
   * using Processing's current random number generator
   * @param parent current PApplet instance
   * @return the random PVector
   */
  static public PVector random2D(PApplet parent) {
    return random2D(null, parent);
  }

  /**
   * Set a 2D vector to a random unit vector with a random direction
   * @param target the target vector (if null, a new vector will be created)
   * @return the random PVector
   */
  static public PVector random2D(PVector target) {
    return random2D(target, null);
  }


  /**
   * Make a new 2D unit vector with a random direction. Pass in the parent
   * PApplet if you want randomSeed() to work (and be predictable). Or leave
   * it null and be... random.
   * @return the random PVector
   */
  static public PVector random2D(PVector target, PApplet parent) {
    return (parent == null) ?
      fromAngle((float) (Math.random() * Math.PI*2), target) :
      fromAngle(parent.random(PConstants.TAU), target);
  }


  /**
   *
   * Returns a new 3D unit vector with a random direction. If you pass in
   * <b>this</b> as an argument, it will use the PApplet's random number
   * generator.
   *
   * @webref pvector:method
   * @usage web_application
   * @return the random PVector
   * @webBrief Make a new 3D unit vector with a random direction
   * @see PVector#random2D()
   */
  static public PVector random3D() {
    return random3D(null, null);
  }


  /**
   * Make a new 3D unit vector with a random direction
   * using Processing's current random number generator
   * @param parent current PApplet instance
   * @return the random PVector
   */
  static public PVector random3D(PApplet parent) {
    return random3D(null, parent);
  }


  /**
   * Set a 3D vector to a random unit vector with a random direction
   * @param target the target vector (if null, a new vector will be created)
   * @return the random PVector
   */
  static public PVector random3D(PVector target) {
    return random3D(target, null);
  }


  /**
   * Make a new 3D unit vector with a random direction
   * @return the random PVector
   */
  static public PVector random3D(PVector target, PApplet parent) {
    float angle;
    float vz;
    if (parent == null) {
      angle = (float) (Math.random()*Math.PI*2);
      vz    = (float) (Math.random()*2-1);
    } else {
      angle = parent.random(PConstants.TWO_PI);
      vz    = parent.random(-1,1);
    }
    float vx = (float) (Math.sqrt(1-vz*vz)*Math.cos(angle));
    float vy = (float) (Math.sqrt(1-vz*vz)*Math.sin(angle));
    if (target == null) {
      target = new PVector(vx, vy, vz);
      //target.normalize(); // Should be unnecessary
    } else {
      target.set(vx,vy,vz);
    }
    return target;
  }


  /**
   *
   * Calculates and returns a new 2D unit vector from the specified angle value
   * (in radians).
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @webBrief Make a new 2D unit vector from an angle
   * @param angle the angle in radians
   * @return the new unit PVector
   */
  static public PVector fromAngle(float angle) {
    return fromAngle(angle,null);
  }


  /**
   * Make a new 2D unit vector from an angle
   *
   * @param target the target vector (if null, a new vector will be created)
   * @return the PVector
   */
  static public PVector fromAngle(float angle, PVector target) {
    if (target == null) {
      target = new PVector((float)Math.cos(angle),(float)Math.sin(angle),0);
    } else {
      target.set((float)Math.cos(angle),(float)Math.sin(angle),0);
    }
    return target;
  }


  /**
   *
   * Copies the components of the vector and returns the result as a <b>PVector</b>.
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @webBrief  Get a copy of the vector
   */
  public PVector copy() {
    return new PVector(x, y, z);
  }


  @Deprecated
  public PVector get() {
    return copy();
  }


  /**
   * @param target
   */
  public float[] get(float[] target) {
    if (target == null) {
      return new float[] { x, y, z };
    }
    if (target.length >= 2) {
      target[0] = x;
      target[1] = y;
    }
    if (target.length >= 3) {
      target[2] = z;
    }
    return target;
  }


  /**
   *
   * Calculates the magnitude (length) of the vector and returns the result
   * as a float (this is simply the equation <em>sqrt(x*x + y*y + z*z)</em>.)
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @webBrief  Calculate the magnitude of the vector
   * @return magnitude (length) of the vector
   * @see PVector#magSq()
   */
  public float mag() {
    return (float) Math.sqrt(x*x + y*y + z*z);
  }


  /**
   *
   * Calculates the magnitude (length) of the vector, squared. This method is
   * often used to improve performance since, unlike <b>mag()</b>, it does not
   * require a <b>sqrt()</b> operation.
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @webBrief Calculate the magnitude of the vector, squared
   * @return squared magnitude of the vector
   * @see PVector#mag()
   */
  public float magSq() {
    return (x*x + y*y + z*z);
  }


  /**
   *
   * Adds x, y, and z components to a vector, adds one vector to another, or adds
   * two independent vectors together. The version of the method that adds two
   * vectors together is a static method and returns a new <b>PVector</b>, the others act
   * directly on the vector itself. See the examples for more context.
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @param v the vector to be added
   * @webBrief Adds x, y, and z components to a vector, one vector to another, or
   *           two independent vectors
   */
  public PVector add(PVector v) {
    x += v.x;
    y += v.y;
    z += v.z;
    return this;
  }


  /**
   * @param x x component of the vector
   * @param y y component of the vector
   */
  public PVector add(float x, float y) {
    this.x += x;
    this.y += y;
    return this;
  }


  /**
   * @param z z component of the vector
   */
  public PVector add(float x, float y, float z) {
    this.x += x;
    this.y += y;
    this.z += z;
    return this;
  }


  /**
   * Add two vectors
   * @param v1 a vector
   * @param v2 another vector
   */
  static public PVector add(PVector v1, PVector v2) {
    return add(v1, v2, null);
  }


  /**
   * Add two vectors into a target vector
   * @param target the target vector (if null, a new vector will be created)
   */
  static public PVector add(PVector v1, PVector v2, PVector target) {
    if (target == null) {
      target = new PVector(v1.x + v2.x,v1.y + v2.y, v1.z + v2.z);
    } else {
      target.set(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }
    return target;
  }


  /**
   *
   * Subtracts x, y, and z components from a vector, subtracts one vector from
   * another, or subtracts two independent vectors. The version of the method that
   * substracts two vectors is a static method and returns a <b>PVector</b>, the others
   * act directly on the vector. See the examples for more context. In all cases,
   * the second vector (v2) is subtracted from the first (v1), resulting in v1-v2.
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @param v any variable of type PVector
   * @webBrief Subtract x, y, and z components from a vector, one vector from
   *           another, or two independent vectors
   */
  public PVector sub(PVector v) {
    x -= v.x;
    y -= v.y;
    z -= v.z;
    return this;
  }


  /**
   * @param x the x component of the vector
   * @param y the y component of the vector
   */
  public PVector sub(float x, float y) {
    this.x -= x;
    this.y -= y;
    return this;
  }


  /**
   * @param z the z component of the vector
   */
  public PVector sub(float x, float y, float z) {
    this.x -= x;
    this.y -= y;
    this.z -= z;
    return this;
  }


  /**
   * Subtract one vector from another
   * @param v1 the x, y, and z components of a PVector object
   * @param v2 the x, y, and z components of a PVector object
   */
  static public PVector sub(PVector v1, PVector v2) {
    return sub(v1, v2, null);
  }


  /**
   * Subtract one vector from another and store in another vector
   * @param target PVector in which to store the result
   */
  static public PVector sub(PVector v1, PVector v2, PVector target) {
    if (target == null) {
      target = new PVector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    } else {
      target.set(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }
    return target;
  }


  /**
   *
   * Multiplies a vector by a scalar. The version of the method that uses a float
   * acts directly on the vector upon which it is called (as in the first example
   * above). The versions that receive both a <b>PVector</b> and a float as arguments are
   * static methods, and each returns a new <b>PVector</b> that is the result of the
   * multiplication operation. Both examples above produce the same visual output.
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @webBrief Multiply a vector by a scalar
   * @param n the number to multiply with the vector
   */
  public PVector mult(float n) {
    x *= n;
    y *= n;
    z *= n;
    return this;
  }


  /**
   * @param v the vector to multiply by the scalar
   */
  static public PVector mult(PVector v, float n) {
    return mult(v, n, null);
  }


  /**
   * Multiply a vector by a scalar, and write the result into a target PVector.
   * @param target PVector in which to store the result
   */
  static public PVector mult(PVector v, float n, PVector target) {
    if (target == null) {
      target = new PVector(v.x*n, v.y*n, v.z*n);
    } else {
      target.set(v.x*n, v.y*n, v.z*n);
    }
    return target;
  }


  /**
   *
   * Divides a vector by a scalar. The version of the method that uses a float
   * acts directly on the vector upon which it is called (as in the first example
   * above). The version that receives both a <b>PVector</b> and a <b>float</b> as arguments is
   * a static methods, and returns a new <b>PVector</b> that is the result of the
   * division operation. Both examples above produce the same visual output.
   *
   * @webref pvector:method
   * @usage web_application
   * @webBrief Divide a vector by a scalar
   * @param n the number by which to divide the vector
   */
  public PVector div(float n) {
    x /= n;
    y /= n;
    z /= n;
    return this;
  }


  /**
   * Divide a vector by a scalar and return the result in a new vector.
   * @param v the vector to divide by the scalar
   * @return a new vector that is v1 / n
   */
  static public PVector div(PVector v, float n) {
    return div(v, n, null);
  }


  /**
   * Divide a vector by a scalar and store the result in another vector.
   * @param target PVector in which to store the result
   */
  static public PVector div(PVector v, float n, PVector target) {
    if (target == null) {
      target = new PVector(v.x/n, v.y/n, v.z/n);
    } else {
      target.set(v.x/n, v.y/n, v.z/n);
    }
    return target;
  }


  /**
   *
   * Calculates the Euclidean distance between two points (considering a
   * point as a vector object).
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @param v the x, y, and z coordinates of a PVector
   * @webBrief  Calculate the distance between two points
   */
  public float dist(PVector v) {
    float dx = x - v.x;
    float dy = y - v.y;
    float dz = z - v.z;
    return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
  }


  /**
   * @param v1 any variable of type PVector
   * @param v2 any variable of type PVector
   * @return the Euclidean distance between v1 and v2
   */
  static public float dist(PVector v1, PVector v2) {
    float dx = v1.x - v2.x;
    float dy = v1.y - v2.y;
    float dz = v1.z - v2.z;
    return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
  }


  /**
   *
   * Calculates the dot product of two vectors.
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @param v any variable of type PVector
   * @return the dot product
   * @webBrief  Calculate the dot product of two vectors
   */
  public float dot(PVector v) {
    return x*v.x + y*v.y + z*v.z;
  }


  /**
   * @param x x component of the vector
   * @param y y component of the vector
   * @param z z component of the vector
   */
  public float dot(float x, float y, float z) {
    return this.x*x + this.y*y + this.z*z;
  }


  /**
   * @param v1 any variable of type PVector
   * @param v2 any variable of type PVector
   */
  static public float dot(PVector v1, PVector v2) {
    return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
  }


  /**
   *
   * Calculates and returns a vector composed of the cross product between
   * two vectors.
   *
   *
   * @webref pvector:method
   * @param v the vector to calculate the cross product
   * @webBrief  Calculate and return the cross product
   */
  public PVector cross(PVector v) {
    return cross(v, null);
  }


  /**
   * @param v any variable of type PVector
   * @param target PVector to store the result
   */
  public PVector cross(PVector v, PVector target) {
    float crossX = y * v.z - v.y * z;
    float crossY = z * v.x - v.z * x;
    float crossZ = x * v.y - v.x * y;

    if (target == null) {
      target = new PVector(crossX, crossY, crossZ);
    } else {
      target.set(crossX, crossY, crossZ);
    }
    return target;
  }


  /**
   * @param v1 any variable of type PVector
   * @param v2 any variable of type PVector
   * @param target PVector to store the result
   */
  static public PVector cross(PVector v1, PVector v2, PVector target) {
    float crossX = v1.y * v2.z - v2.y * v1.z;
    float crossY = v1.z * v2.x - v2.z * v1.x;
    float crossZ = v1.x * v2.y - v2.x * v1.y;

    if (target == null) {
      target = new PVector(crossX, crossY, crossZ);
    } else {
      target.set(crossX, crossY, crossZ);
    }
    return target;
  }


  /**
   *
   * Normalize the vector to length 1 (make it a unit vector).
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @webBrief  Normalize the vector to a length of 1
   */
  public PVector normalize() {
    float m = mag();
    if (m != 0 && m != 1) {
      div(m);
    }
    return this;
  }


  /**
   * @param target Set to null to create a new vector
   * @return a new vector (if target was null), or target
   */
  public PVector normalize(PVector target) {
    if (target == null) {
      target = new PVector();
    }
    float m = mag();
    if (m > 0) {
      target.set(x/m, y/m, z/m);
    } else {
      target.set(x, y, z);
    }
    return target;
  }


  /**
   *
   * Limit the magnitude of this vector to the value used for the <b>max</b> parameter.
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @param max the maximum magnitude for the vector
   * @webBrief  Limit the magnitude of the vector
   */
  public PVector limit(float max) {
    if (magSq() > max*max) {
      normalize();
      mult(max);
    }
    return this;
  }


  /**
   *
   * Set the magnitude of this vector to the value used for the <b>len</b> parameter.
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @param len the new length for this vector
   * @webBrief  Set the magnitude of the vector
   */
  public PVector setMag(float len) {
    normalize();
    mult(len);
    return this;
  }


  /**
   * Sets the magnitude of this vector, storing the result in another vector.
   * @param target Set to null to create a new vector
   * @param len the new length for the new vector
   * @return a new vector (if target was null), or target
   */
  public PVector setMag(PVector target, float len) {
    target = normalize(target);
    target.mult(len);
    return target;
  }


  /**
   *
   * Calculate the vector's direction, that is, the angle this vector makes
   * with the positive X axis (only 2D vectors)
   * 
   *
   * @webref pvector:method
   * @usage web_application
   * @return the direction of the vector (angle with positive X axis)
   * @webBrief  Calculate the angle of rotation for this vector
   * @see PVector#setHeading()
   */
  public float heading() {
    float angle = (float) Math.atan2(y, x);
    return angle;
  }


  @Deprecated
  public float heading2D() {
    return heading();
  }

  /**
   *
   * Sets the angle this vector makes with the positive X axis (only 2D vectors)
   * This is equivalent to changing the vector's direction to the given value.
   *
   * @webref pvector:method
   * @usage web_application
   * @param angle the direction of the resultant vector
   * @return this vector, rotated to have the given direction
   * @webBrief  Set the direction for this vector
   * @see PVector#heading()
   */
  public PVector setHeading(float angle) {
    float m = mag();
    x = (float) (m * Math.cos(angle));
    y = (float) (m * Math.sin(angle));
    return this;
  }


  /**
   *
   * Rotate the vector by an angle (only 2D vectors), magnitude remains the same
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @webBrief  Rotate the vector by an angle (2D only)
   * @param theta the angle of rotation
   */
  public PVector rotate(float theta) {
    float temp = x;
    // Might need to check for rounding errors like with angleBetween function?
    x = x*PApplet.cos(theta) - y*PApplet.sin(theta);
    y = temp*PApplet.sin(theta) + y*PApplet.cos(theta);
    return this;
  }


  /**
   *
   * Calculates linear interpolation from one vector to another vector. (Just like
   * regular <b>lerp()</b>, but for vectors.)<br />
   * <br />
   * Note that there is one <em>static</em> version of this method, and two
   * <em>non-static</em> versions. The static version, <b>lerp(v1, v2, amt)</b> is
   * given the two vectors to interpolate and returns a new PVector object. The
   * static version is used by referencing the PVector class directly. (See the
   * middle example above.) The non-static versions, <b>lerp(v, amt)</b> and
   * <b>lerp(x, y, z, amt)</b>, do not create a new PVector, but transform the
   * values of the <b>PVector</b> on which they are called. These non-static versions
   * perform the same operation, but the former takes another vector as input,
   * while the latter takes three float values. (See the top and bottom examples
   * above, respectively.)
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @webBrief Linear interpolate the vector to another vector
   * @param v   the vector to lerp to
   * @param amt The amount of interpolation; some value between 0.0 (old vector)
   *            and 1.0 (new vector). 0.1 is very near the old vector; 0.5 is
   *            halfway in between.
   * @see PApplet#lerp(float, float, float)
   */
  public PVector lerp(PVector v, float amt) {
    x = PApplet.lerp(x, v.x, amt);
    y = PApplet.lerp(y, v.y, amt);
    z = PApplet.lerp(z, v.z, amt);
    return this;
  }


  /**
   * Linear interpolate between two vectors (returns a new PVector object)
   * @param v1 the vector to start from
   * @param v2 the vector to lerp to
   */
  public static PVector lerp(PVector v1, PVector v2, float amt) {
    PVector v = v1.copy();
    v.lerp(v2, amt);
    return v;
  }


  /**
   * Linear interpolate the vector to x,y,z values
   * @param x the x component to lerp to
   * @param y the y component to lerp to
   * @param z the z component to lerp to
   */
  public PVector lerp(float x, float y, float z, float amt) {
    this.x = PApplet.lerp(this.x, x, amt);
    this.y = PApplet.lerp(this.y, y, amt);
    this.z = PApplet.lerp(this.z, z, amt);
    return this;
  }


  /**
   *
   * Calculates and returns the angle (in radians) between two vectors.
   *
   *
   * @webref pvector:method
   * @usage web_application
   * @param v1 the x, y, and z components of a PVector
   * @param v2 the x, y, and z components of a PVector
   * @webBrief  Calculate and return the angle between two vectors
   */
  static public float angleBetween(PVector v1, PVector v2) {

    // We get NaN if we pass in a zero vector which can cause problems
    // Zero seems like a reasonable angle between a (0,0,0) vector and something else
    if (v1.x == 0 && v1.y == 0 && v1.z == 0 ) return 0.0f;
    if (v2.x == 0 && v2.y == 0 && v2.z == 0 ) return 0.0f;

    double dot = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    double v1mag = Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
    double v2mag = Math.sqrt(v2.x * v2.x + v2.y * v2.y + v2.z * v2.z);
    // This should be a number between -1 and 1, since it's "normalized"
    double amt = dot / (v1mag * v2mag);
    // But if it's not due to rounding error, then we need to fix it
    // https://github.com/processing/processing/issues/379
    // Otherwise if outside the range, acos() will return NaN
    // http://www.cppreference.com/wiki/c/math/acos
    if (amt <= -1) {
      return PConstants.PI;
    } else if (amt >= 1) {
      // https://github.com/processing/processing/issues/474
      return 0;
    }
    return (float) Math.acos(amt);
  }


  @Override
  public String toString() {
    return "[ " + x + ", " + y + ", " + z + " ]";
  }


  /**
   *
   * Return a representation of this vector as a float array. This is only for
   * temporary use. If used in any other fashion, the contents should be copied by
   * using the <b>copy()</b> method to copy into your own array.
   *
   *
   * @webref pvector:method
   * @usage: web_application
   * @webBrief Return a representation of the vector as a float array
   */
  public float[] array() {
    if (array == null) {
      array = new float[3];
    }
    array[0] = x;
    array[1] = y;
    array[2] = z;
    return array;
  }


  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof PVector)) {
      return false;
    }
    final PVector p = (PVector) obj;
    return x == p.x && y == p.y && z == p.z;
  }


  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + Float.floatToIntBits(x);
    result = 31 * result + Float.floatToIntBits(y);
    result = 31 * result + Float.floatToIntBits(z);
    return result;
  }
}
