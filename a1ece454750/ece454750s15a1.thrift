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
  4: i32 logRounds
}

struct GroupMembers
{
  1: list<string> groupMem
}

struct BEJoinProtocol
{
  1: string host
  2: i32 pportNum
  3: i32 mportNum
  4: bool isBackEnd
  5: i32 numCore
}

service A1Password
{
   string hashPassword(1:string password, 2:i32 logRounds),
   bool checkPassword(1:string password, 2:string hash)
}

service A1Management
{
   //void Join(1:BEJoinProtocol joinProto),
   //void Gossip(1:list<BEJoinProtocol> listOfBE),
   PerfCounters getPerfCounters(),
   list<string> getGroupMembers()
}

