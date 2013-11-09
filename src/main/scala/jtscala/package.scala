import com.vividsolutions.jts.geom

package object jtscala {
  implicit def tupleToCoordinate2(t:(Double,Double)) =
    new geom.Coordinate(t._1,t._2)

  implicit def tupleToCoordinate3(t:(Double,Double,Double)) =
    new geom.Coordinate(t._1,t._2,t._3)

  implicit def coordinateToTuple2(c:geom.Coordinate) =
    (c.x,c.y)

  implicit def coordinateToTuple3(c:geom.Coordinate) =
    (c.x,c.y,c.z)

  implicit def tupleSeqToCoordinateArray2(ts:Seq[(Double,Double)]) =
    ts map(t => new geom.Coordinate(t._1,t._2)) toArray

  implicit def tupleSeqToCoordinateArray3(ts:Seq[(Double,Double,Double)]) =
    ts map(t => new geom.Coordinate(t._1,t._2,t._3)) toArray

  implicit def coordinateArayToTupleSeq2(cs:Array[geom.Coordinate]) =
    cs map(c => (c.x,c.y)) toSeq

  implicit def coordinateArayToTupleSeq3(cs:Array[geom.Coordinate]) =
    cs map(c => (c.x,c.y,c.z)) toSeq
}
