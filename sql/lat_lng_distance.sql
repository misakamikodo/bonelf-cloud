# MySQL 经纬度距离计算函数 建议使用redis GEO 存储计算
CREATE FUNCTION `lat_lng_distance` (lat1 FLOAT, lon1 FLOAT, lat2 FLOAT, lon2 FLOAT)
    RETURNS FLOAT
    DETERMINISTIC
BEGIN
    RETURN ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((lat1 * PI() / 180 - lat2 * PI() / 180) / 2), 2)
        + COS(lat1 * PI() / 180) * COS(lat2 * PI() / 180)
                                              * POW(SIN(( lon1 * PI() / 180 - lon2 * PI() / 180 ) / 2),2))),2);
END