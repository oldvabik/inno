SELECT
    request_at AS Day,
    ROUND(AVG(CASE WHEN status != 'completed' THEN 1 ELSE 0 END), 2) AS "Cancellation rate"
FROM Trips
WHERE 
    request_at BETWEEN '2013-10-01' AND '2013-10-03'
    AND client_id IN (SELECT users_id FROM Users WHERE banned = 'No')
    AND driver_id IN (SELECT users_id FROM Users WHERE banned = 'No')
GROUP BY request_at
ORDER BY request_at