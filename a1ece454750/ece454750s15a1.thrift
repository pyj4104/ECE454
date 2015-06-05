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

struct JoinProtocol
{
  1: string host
  2: i32 pportNum
  3: i32 mportNum
  4: bool isBackEnd
  5: i32 numCore
}

struct FEJoinResponse
{
  1: list<string> activeBEs
  2: map<string, JoinProtocol> aliveBEs
  3: map<string, JoinProtocol> aliveFEs
}

exception ServiceUnavailableException
{
  1:string msg
}

service A1Password
{
   //string hashPassword(1:string password, 2:i32 logRounds) throws (1:ServiceUnavailableException X),
   //bool checkPassword(1:string password, 2:string hash) throws (1:ServiceUnavailableException X)
   string hashPassword(1:string password, 2:i32 logRounds),
   bool checkPassword(1:string password, 2:string hash)
}

service A1Management
{
   bool join(1:JoinProtocol joinProto),
   FEJoinResponse feJoin(1:JoinProtocol joinProto),
   //void gossip(1:list<JoinProtocol> listOfBE),
   PerfCounters getPerfCounters(),
   list<string> getGroupMembers()
}

