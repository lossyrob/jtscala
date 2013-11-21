package jtscala

abstract sealed trait LinePointIntersectionResult
abstract sealed trait PolygonLineIntersectionResult
abstract sealed trait PolygonPolygonIntersectionResult

case object NoResult extends LinePointIntersectionResult
                        with PolygonLineIntersectionResult
                        with PolygonPolygonIntersectionResult

case class PointResult(p:Point) extends LinePointIntersectionResult
                                   with PolygonLineIntersectionResult
                                   with PolygonPolygonIntersectionResult

case class LineResult(l:Line) extends LinePointIntersectionResult
                                 with PolygonLineIntersectionResult
                                 with PolygonPolygonIntersectionResult

case class LineSeqResult(ls:Seq[Line]) extends PolygonLineIntersectionResult
                                          with PolygonPolygonIntersectionResult

case class PointSeqResult(ls:Seq[Point]) extends PolygonPolygonIntersectionResult

case class PolygonResult(p:Polygon) extends PolygonPolygonIntersectionResult

case class PolygonSeqResult(ps:Seq[Polygon]) extends PolygonPolygonIntersectionResult

case class GeometryCollectionResult(gc:GeometryCollection) extends PolygonPolygonIntersectionResult

// case object NoIntersection extends Intersection
//                               with LLIntersection 
//                               with LAIntersection
// case class PointIntersection(p:Point) extends LineLineIntersection
// case class LineIntersection(l:Line) extends LineLineIntersection
