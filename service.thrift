namespace java tut

struct item
{
	1: i32 key
	2: string value
}

service MyService
{
	i32 add(1:i32 num1, 2:i32 num2),
	item getitem(1:i32 key),
	bool putitem(1:item item)
}

