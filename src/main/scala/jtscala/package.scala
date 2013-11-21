import com.vividsolutions.jts.{geom=>jts}

import scala.collection.mutable

package object jtscala {
  implicit def tupleToCoordinate2(t:(Double,Double)) =
    new jts.Coordinate(t._1,t._2)

  implicit def tupleToCoordinate3(t:(Double,Double,Double)) =
    new jts.Coordinate(t._1,t._2,t._3)

  implicit def coordinateToTuple2(c:jts.Coordinate) =
    (c.x,c.y)

  implicit def coordinateToTuple3(c:jts.Coordinate) =
    (c.x,c.y,c.z)

  implicit def tupleSeqToCoordinateArray2(ts:Seq[(Double,Double)]) =
    ts map(t => new jts.Coordinate(t._1,t._2)) toArray

  implicit def tupleSeqToCoordinateArray3(ts:Seq[(Double,Double,Double)]) =
    ts map(t => new jts.Coordinate(t._1,t._2,t._3)) toArray

  implicit def coordinateArayToTupleSeq2(cs:Array[jts.Coordinate]) =
    cs map(c => (c.x,c.y)) toSeq

  implicit def coordinateArayToTupleSeq3(cs:Array[jts.Coordinate]) =
    cs map(c => (c.x,c.y,c.z)) toSeq

 implicit def multiPointToSeqPoint(mp:jts.MultiPoint):Seq[Point] = {
    val len = mp.getNumGeometries
    (for(i <- 0 until len) yield {
      Point(mp.getGeometryN(i).asInstanceOf[jts.Point])
    }).toSeq
  }

  implicit def multiLineToSeqLine(ml:jts.MultiLineString):Seq[Line] = {
    val len = ml.getNumGeometries
    (for(i <- 0 until len) yield {
      Line(ml.getGeometryN(i).asInstanceOf[jts.LineString])
    }).toSeq
  }

  implicit def multiPolygonToSeqPolygon(mp:jts.MultiPolygon):Seq[Polygon] = {
    val len = mp.getNumGeometries
    (for(i <- 0 until len) yield {
      Polygon(mp.getGeometryN(i).asInstanceOf[jts.Polygon])
    }).toSeq
  }

  implicit def geometryCollectionToSeqGeometry(gc:jts.GeometryCollection):Seq[Geometry] = {
    val len = gc.getNumGeometries
    (for(i <- 0 until len) yield {
      gc.getGeometryN(i) match {
        case p:jts.Point => Seq[Geometry](Point(p))
        case mp:jts.MultiPoint => multiPointToSeqPoint(mp)
        case l:jts.LineString => Seq[Geometry](Line(l))
        case ml:jts.MultiLineString => multiLineToSeqLine(ml)
        case p:jts.Polygon => Seq[Geometry](Polygon(p))
        case mp:jts.MultiPolygon => multiPolygonToSeqPolygon(mp)
        case gc:jts.GeometryCollection => geometryCollectionToSeqGeometry(gc)
      }
    }).toSeq.flatten
  }

  implicit def seqPointToPointSeq(ps:Seq[Point]) = PointSeq(ps)
  implicit def seqLineToLineSeq(ps:Seq[Line]) = LineSeq(ps)
  implicit def seqPolygonToPolygonSeq(ps:Seq[Polygon]) = PolygonSeq(ps)

  implicit def seqGeometryToGeometryCollection(gs:Seq[Geometry]) = {
    val points = mutable.ListBuffer[Point]()
    val lines = mutable.ListBuffer[Line]()
    val polygons = mutable.ListBuffer[Polygon]()
    for(g <- gs) { 
      g match {
        case p:Point => points += p
        case l:Line => lines += l
        case p:Polygon => polygons += p
        case _ => sys.error(s"Unknown Geometry type: $g")
      }
    }
    GeometryCollection(points.toSeq,lines.toSeq,polygons.toSeq)
  }
}
