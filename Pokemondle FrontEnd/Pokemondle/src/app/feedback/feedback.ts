export enum Answer{
    Greater='Greater',
    Equal='Equal',
    Lower='Lower'
}

 export class Feedback {
    constructor(
        public checkName: boolean,
        public checkGeneration: Answer,
        public checkType1: boolean,
        public  checkType2: boolean,
        public checkHeight: Answer,
        public checkWeight: Answer,
        resultName: string
    ){}
}
