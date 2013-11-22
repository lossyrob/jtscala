package jtscala

import com.vividsolutions.jts.{geom=>jts}
import GeomFactory._

case class LineSet(ls:Set[Line]) extends GeometrySet {
  val geom = factory.createMultiLineString(ls.map(_.geom).toArray)

  private def _intersection(g:Geometry):LineSetIntersectionResult =
    geom.intersection(g.geom) 


  def intersection(l:Line):LineSetIntersectionResult = _intersection(l)
  def &(l:Line):LineSetIntersectionResult = intersection(l)

  def intersection(p:Polygon):LineSetIntersectionResult = _intersection(p)
  def &(p:Polygon):LineSetIntersectionResult = intersection(p)

  def intersection(ls:LineSet):LineSetIntersectionResult = _intersection(ls)
  def &(ls:LineSet):LineSetIntersectionResult = intersection(ls)

  def intersection(ps:PolygonSet):LineSetIntersectionResult = _intersection(ps)
  def &(ps:PolygonSet):LineSetIntersectionResult = intersection(ps)
}
