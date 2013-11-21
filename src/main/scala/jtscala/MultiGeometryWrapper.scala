package jtscala

import GeomFactory._

trait MultiGeometryWrapper extends Geometry {

}

case class PointSeq(ps:Seq[Point]) extends MultiGeometryWrapper {
  val geom = factory.createMultiPoint(ps.map(_.geom).toArray)
}

case class LineSeq(ls:Seq[Line]) extends MultiGeometryWrapper {
  val geom = factory.createMultiLineString(ls.map(_.geom).toArray)
}

case class PolygonSeq(ps:Seq[Polygon]) extends MultiGeometryWrapper {
  val geom = factory.createMultiPolygon(ps.map(_.geom).toArray)
}

