var dict1 = {'a':[],'b':[]}
dict1['a'].push("fdsfds")
dict1['a'].push(5)
dict1['a'].push(6)
dict1['a'].push(7)
var combined = [].concat(dict1['a'],dict1['b'])
console.log(dict1['a'])
