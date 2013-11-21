package jtscala
import GeomFactory._

import com.vividsolutions.jts.{geom=>jts}
import scala.collection.mutable

case class GeometryCollection(points:Seq[Point],lines:Seq[Line],polygons:Seq[Polygon],gc:jts.GeometryCollection) extends Geometry {
  val geom = factory.createGeometryCollection((points ++ lines ++ polygons).map(_.geom).toArray)
}

object GeometryCollection {
  def apply(points:Seq[Point],lines:Seq[Line],polygons:Seq[Polygon]):GeometryCollection = {
    val geom = factory.createGeometryCollection((points ++ lines ++ polygons).map(_.geom).toArray)
    GeometryCollection(points,lines,polygons,geom)
  }

  implicit def jtsToGeometryCollection(gc:jts.GeometryCollection):GeometryCollection = {
    def collectGeometries(gc:jts.GeometryCollection):(Seq[Point],Seq[Line],Seq[Polygon]) = {
      val points = mutable.ListBuffer[Point]()
      val lines = mutable.ListBuffer[Line]()
      val polygons = mutable.ListBuffer[Polygon]()

      val len = gc.getNumGeometries
      for(i <- 0 until len) {
        gc.getGeometryN(i) match {
          case p:jts.Point => points += p
          case mp:jts.MultiPoint => points ++= mp
          case l:jts.LineString => lines += l
          case ml:jts.MultiLineString => lines ++= ml
          case p:jts.Polygon => polygons += p
          case mp:jts.MultiPolygon => polygons ++= mp
          case gc:jts.GeometryCollection => 
            val (ps,ls,polys) = collectGeometries(gc)
            points ++= ps
            lines ++= ls
            polygons ++= polys
        }
      }
      (points.toSeq,lines.toSeq,polygons.toSeq)
    }
    val (points,lines,polygons) = collectGeometries(gc)
    GeometryCollection(points,lines,polygons,gc)
  }
}
