package jtscala

import com.vividsolutions.jts.{geom=>jts}

// Intersection
abstract sealed trait PointIntersectionResult
object PointIntersectionResult {
  implicit def jtsToResult(geom:jts.Geometry):PointIntersectionResult =
    geom match {
      case p:jts.Point => PointResult(p)
      case _ => NoResult
    }
}

abstract sealed trait LineLineIntersectionResult
object LineLineIntersectionResult {
  implicit def jtsToResult(geom:jts.Geometry):LineLineIntersectionResult =
    geom match {
      case p:jts.Point => PointResult(p)
      case l:jts.LineString => LineResult(l)
      case _ => NoResult
    }
}

abstract sealed trait PolygonLineIntersectionResult
object PolygonLineIntersectionResult {
  implicit def jtsToResult(geom:jts.Geometry):PolygonLineIntersectionResult =
    geom match {
      case p:jts.Point => PointResult(p)
      case l:jts.LineString => LineResult(l)
      case l:jts.MultiLineString => LineSetResult(l)
      case _ => NoResult
    }
}

abstract sealed trait PolygonPolygonIntersectionResult
object PolygonPolygonIntersectionResult {
  implicit def jtsToResult(geom:jts.Geometry):PolygonPolygonIntersectionResult =
    geom match {
      case p:jts.Point => PointResult(p)
      case ps:jts.MultiPoint => PointSetResult(ps)
      case l:jts.LineString => LineResult(l)
      case ls:jts.MultiLineString => LineSetResult(ls)
      case p:jts.Polygon => PolygonResult(p)
      case ps:jts.MultiPolygon => PolygonSetResult(ps)
      case gc:jts.GeometryCollection => GeometryCollectionResult(gc)
      case _ => NoResult
    }
}

abstract sealed trait PointSetIntersectionResult
object PointSetIntersectionResult {
  implicit def jtsToResult(geom:jts.Geometry):PointSetIntersectionResult =
    geom match {
      case p:jts.Point => if(p.isEmpty) NoResult else PointResult(p)
      case mp:jts.MultiPoint => PointSetResult(mp)
      case x => 
        sys.error(s"Unexpected result for PointSet intersection: ${geom.getGeometryType}")
    }
}

abstract sealed trait LineSetIntersectionResult
object LineSetIntersectionResult {
  implicit def jtsToResult(geom:jts.Geometry): LineSetIntersectionResult =
    geom match {
      case p: jts.Point => PointResult(p)
      case l: jts.LineString => if(l.isEmpty) NoResult else LineResult(l)
      case mp: jts.MultiPoint => PointSetResult(mp)
      case ml: jts.MultiLineString => LineSetResult(ml)
      case gc: jts.GeometryCollection => GeometryCollectionResult(gc)
      case x => 
        sys.error(s"Unexpected result for LineSet intersection: ${geom.getGeometryType}")
    }
}

abstract sealed trait PolygonSetIntersectionResult
object PolygonSetIntersectionResult {
  implicit def jtsToResult(geom:jts.Geometry): PolygonSetIntersectionResult =
    geom match {
      case p: jts.Point => PointResult(p)
      case l: jts.LineString => LineResult(l)
      case p: jts.Polygon => if(p.isEmpty) NoResult else PolygonResult(p)
      case mp: jts.MultiPoint => PointSetResult(mp)
      case ml: jts.MultiLineString => LineSetResult(ml)
      case mp: jts.MultiPolygon => PolygonSetResult(mp)
      case gc: jts.GeometryCollection => GeometryCollectionResult(gc)
      case _ => 
        sys.error(s"Unexpected result for PolygonSet intersection: ${geom.getGeometryType}")
    }
}

// Union
abstract sealed trait PolygonPointUnionResult
abstract sealed trait PolygonLineUnionResult
abstract sealed trait PolygonPolygonUnionResult

case object NoResult 
    extends PointIntersectionResult
       with LineLineIntersectionResult
       with PolygonLineIntersectionResult
       with PolygonPolygonIntersectionResult
       with PointSetIntersectionResult
       with LineSetIntersectionResult
       with PolygonSetIntersectionResult

case class PointResult(p:Point) 
    extends PointIntersectionResult
       with LineLineIntersectionResult
       with PolygonLineIntersectionResult
       with PolygonPolygonIntersectionResult
       with PointSetIntersectionResult
       with LineSetIntersectionResult
       with PolygonSetIntersectionResult

case class LineResult(l:Line) 
    extends LineLineIntersectionResult
       with PolygonLineIntersectionResult
       with PolygonPolygonIntersectionResult
       with LineSetIntersectionResult
       with PolygonSetIntersectionResult

case class PolygonResult(p:Polygon) 
    extends PolygonPolygonIntersectionResult
       with PolygonPointUnionResult
       with PolygonLineUnionResult
       with PolygonPolygonUnionResult
       with PolygonSetIntersectionResult

case class PointSetResult(ls:Set[Point]) 
    extends PolygonPolygonIntersectionResult
       with PointSetIntersectionResult
       with LineSetIntersectionResult
       with PolygonSetIntersectionResult

case class LineSetResult(ls:Set[Line]) 
    extends PolygonLineIntersectionResult
       with PolygonPolygonIntersectionResult
       with LineSetIntersectionResult
       with PolygonSetIntersectionResult

case class PolygonSetResult(ps:Set[Polygon]) 
    extends PolygonPolygonIntersectionResult
       with PolygonPolygonUnionResult
       with PolygonSetIntersectionResult

case class GeometryCollectionResult(gc:GeometryCollection) 
    extends PolygonPointUnionResult
       with PolygonLineUnionResult
       with PolygonPolygonIntersectionResult
       with LineSetIntersectionResult
       with PolygonSetIntersectionResult