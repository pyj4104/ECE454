namespace java ece454750s15a1

struct PerfCounters
{
  1: i32 numSecondsUp
  2: i32 numRequestsReceived
  3: i32 numRequestsCompleted
}

struct Password
{
  1: string password
  2: string hash
  3: bool plainMatchesHashed
  4: i64 logRounds
}

struct GroupMembers
{
  1: list<string> groupMem
}

service A1Password
{
   string hashPassword(1:string password, 2:i64 logRounds),
   bool checkPassword(1:string password, 2:string hash)
}

service A1Management
{
   PerfCounters getPerfCounters(),
   list<string> getGroupMembers()
}

