package jtscala.check.jts

import com.vividsolutions.jts.geom._

import org.scalacheck._
import Prop._

object MultiPointCheck extends Properties("MultiPoint") {
  import Generators._

  property("buffer => EMPTY") = forAll { (mp: MultiPoint) =>
    mp.buffer(1.0).isEmpty
  }
}
