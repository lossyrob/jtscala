package jtscala

import com.vividsolutions.jts.{geom=>jts}
import GeomFactory._

case class Line(geom:jts.LineString) extends Geometry {
  // Not sure what to do about LinearString, if it really
  // needs to be around...will make construction of Polys 
  // tougher maybe.
  def closed = geom.isClosed

  def crosses(g:Geometry) =
    geom.crosses(g.geom)

  def intersection(p:Point):Option[Point] =
    p.intersection(this)

  def intersection(l:Line):LinePointIntersectionResult =
    geom.intersection(l.geom) match {
      case p:jts.Point => PointResult(p)
      case l:jts.LineString => LineResult(l)
      case _ => NoResult
    }

  def intersection(p:Polygon):PolygonLineIntersectionResult =
    geom.intersection(p.geom) match {
      case p:jts.Point => PointResult(p)
      case l:jts.LineString => LineResult(l)
      case l:jts.MultiLineString => LineSeqResult(l)
      case _ => NoResult
    }

  def difference(g:Geometry):Seq[Line] =
    geom.difference(g.geom) match {
      case ml:jts.MultiLineString =>
        ml
        // val len = ml.getNumGeometries
        // (for(i <- 0 until len) yield { 
        //   Line(ml.getGeometryN(i).asInstanceOf[jts.LineString])
        //  }).toSeq
      case l:jts.LineString =>
        Seq(Line(l))
      case x =>
        assert(x.isEmpty)
        Seq()
    }
}

object Line {
  implicit def jtsToLine(geom:jts.LineString):Line = Line(geom)
}
