local resultList = {}
local limit = tonumber(ARGV[1])
local result = redis.call('LRANGE', KEYS[1], 0, limit - 1)
if table.getn(result) > 0 then
    for i, v in ipairs(result) do
        resultList[i] = v
    end
    redis.call('LTRIM', KEYS[1], limit, -1)
end
return resultList