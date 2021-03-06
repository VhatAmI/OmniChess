package com.donthaveawebsite.mhy.ocfix;
/**
 * Created by Matthew on 3/29/2015.
 */

public class IsValid
        //determines if move is valid
{
    public enum piecetype
    {
        pawn,
        king,
        knight,
        bishop,
        rook,
        queen,
        portal
    }
    piecetype piecetypes;
    //Constructors
    public IsValid(){}

    public IsValid(Piece piece)
    {this.piecetypes = piece.type.piecetypes;}

    public IsValid(piecetype piecetypes)
    {this.piecetypes = piecetypes;}
    //Methods
    public boolean IsMoveValid(Piece piece, Spot Source, Spot Destination, Board theboard)
    {
        IsValid thetype = new IsValid(piece);
        return thetype.Verify(piece, Source, Destination, theboard);
    }

        public boolean Verify(Piece piece, Spot Source, Spot Destination, Board theboard)
         {
            switch(piecetypes)
            {
                case pawn:
                //Does not have enpasant
                return PawnLogicCheck(piece, Source, Destination, theboard);
                case king:
                    //needs logic
                    break;
                case knight:
                    return KnightLogicCheck(piece, Source, Destination);
                case bishop:
                    return BishopLogicCheck(piece, Source, Destination, theboard);
                case rook:
                    return RookLogicCheck(piece, Source, Destination, theboard);
                case queen:
                    return (((BishopLogicCheck(piece,Source,Destination, theboard)) || (RookLogicCheck(piece, Source, Destination, theboard))));
                case portal:
                    //Can't move portals, can't select portals either, HOW DID THIS HAPPEN? Go debug right meow
                    break;
                default:
                    break;

            }

            return false; //Default returns false, piece type not added, or shouldnt have been selected, off to the debug you go
         }

    public Spot OneN(Spot Source, Board theboard)
    {
        return (theboard.getSpot(Source.x, (Source.y + 1)));
    }
    public Spot OneS(Spot Source, Board theboard)
    {
        return (theboard.getSpot(Source.x, (Source.y - 1)));
    }

    public Spot OneR(Spot Source, Board theboard)
    {
        return (theboard.getSpot(Source.x + 1, (Source.y)));
    }
    public Spot OneL(Spot Source, Board theboard)
    {
        return (theboard.getSpot(Source.x - 1, (Source.y)));
    }


    public Spot OneLN(Spot Source, Board theboard) throws ArrayIndexOutOfBoundsException
    {
            return (theboard.getSpot(Source.x - 1, (Source.y + 1)));
    }
    public Spot OneLS(Spot Source, Board theboard) throws ArrayIndexOutOfBoundsException
    {
            return (theboard.getSpot(Source.x - 1, (Source.y - 1)));
    }
    public Spot OneRS(Spot Source, Board theboard) throws ArrayIndexOutOfBoundsException
    {
            return (theboard.getSpot(Source.x + 1, (Source.y - 1)));
    }

    public Spot OneRN(Spot Source, Board theboard) throws ArrayIndexOutOfBoundsException
    {
        return (theboard.getSpot(Source.x + 1, (Source.y + 1)));
    }

    public boolean IsEdge(Spot spot, Board theboard)
    {
        if ( (spot.x == 0 || spot.x == 7))
        {return true;}
        if ( (spot.y == 0 || spot.y == 7))
        {return true;}
        return false;
    }



    private boolean RookLogicCheck(Piece piece,Spot Source,Spot Destination, Board theboard)
    {
        if (Destination.isOccupied() && (Destination.getpiece().getcolor(Destination.getpiece()) == piece.getcolor(piece))) //pieces are same color
            return false;

        //H or V
        if (Source.x - Destination.x == 0)//V
        {

            if(Source.y != Destination.y)
            {
               if (Source.y - Destination.y < 0) //going up the board
               {
                   Spot Sspot = Source;
                   while(Sspot != Destination)
                   {
                      if (OneN(Sspot, theboard).isOccupied())
                      {
                          if (OneN(Sspot, theboard) == Destination)
                          {return true;}
                          return false;
                      }
                       Sspot = OneN(Sspot, theboard);
                   }
                   return true;
               }
                else
               {
                   Spot Sspot = Source;
                   while(Sspot != Destination)
                   {
                       if (OneS(Sspot, theboard).isOccupied())
                       {
                           if (OneS(Sspot, theboard) == Destination)
                           {return true;}
                           return false;
                       }
                       Sspot = OneS(Sspot, theboard);
                   }
                   return true;
               }

            }


        }
        if (Source.y - Destination.y == 0)//H
        {

            if (Source.x != Destination.x) {
                if (Source.x - Destination.x < 0)// going right
                {
                    Spot Sspot = Source;
                    while (Sspot != Destination) {
                        if (OneR(Sspot, theboard).isOccupied()) {
                            if (OneR(Sspot, theboard) == Destination) {
                                return true;
                            }
                            return false;
                        }
                        Sspot = OneR(Sspot, theboard);
                    }
                    return true;
                } else {
                    Spot Sspot = Source;
                    while (Sspot != Destination) {
                        if (OneL(Sspot, theboard).isOccupied()) {
                            if (OneL(Sspot, theboard) == Destination) {
                                return true;
                            }
                            return false;
                        }
                        Sspot = OneL(Sspot, theboard);
                    }
                    return true;
                }
            }
        }

        return false;
    }
    private boolean BishopLogicCheck(Piece piece,Spot Source, Spot Destination, Board theboard)
    {
        if (Destination.isOccupied() && (Destination.getpiece().getcolor(Destination.getpiece()) == piece.getcolor(piece))) //pieces are same color
            return false;

        try
        {
            return  InBishopPath(Source, Destination, theboard);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }
           return false;
    }

    private Boolean InBishopPath(Spot Source, Spot Destination, Board theboard) {
        if (Source.y - Destination.y < 0)
        {
            //NR or NL
            if (Source.x - Destination.x < 0) //going NR
            {
                Spot Sspot = Source;
                while (Sspot != Destination)
                {
                    if (IsEdge(Sspot, theboard)&& Sspot != Source)
                        return false;
                    if (OneRN(Sspot, theboard).isOccupied()) {
                        if (OneRN(Sspot, theboard) == Destination) {
                            return true;
                        }
                        return false;
                    }
                    Sspot = OneRN(Sspot, theboard);
                }
                return true;
            }
            if (Source.x - Destination.x > 0) //going NL
            {
                Spot Sspot = Source;

                while (Sspot != Destination)
                {
                    if (IsEdge(Sspot, theboard) && Sspot != Source)
                        return false;
                    if (OneLN(Sspot, theboard).isOccupied()) {
                        if (OneLN(Sspot, theboard) == Destination) {
                            return true;
                        }
                        return false;
                    }
                    Sspot = OneLN(Sspot, theboard);
                }
                return true;
            }
        }
        if (Source.y - Destination.y > 0)
        {
            //SR or SL
            if (Source.x - Destination.x < 0) //going SR
            {
                Spot Sspot = Source;

                while (Sspot != Destination) {
                    if (IsEdge(Sspot, theboard) && Sspot != Source)
                        return false;
                    if (OneRS(Sspot, theboard).isOccupied()) {
                        if (OneRS(Sspot, theboard) == Destination) {
                            return true;
                        }
                        return false;
                    }
                    Sspot = OneRS(Sspot, theboard);
                }
                return true;
            }
            if (Source.x - Destination.x > 0)
            {
                Spot Sspot = Source;

                while (Sspot != Destination) {
                    if (IsEdge(Sspot, theboard) && Sspot != Source)
                        return false;
                    if (OneLS(Sspot, theboard).isOccupied()) {
                        if (OneLS(Sspot, theboard) == Destination) {
                            return true;
                        }
                        return false;
                    }
                    Sspot = OneLS(Sspot, theboard);
                }
                return true;
            }

        }
        return false;
    }


    private boolean KnightLogicCheck(Piece piece, Spot Source, Spot Destination) {
        if (Destination.isOccupied() && (Destination.getpiece().getcolor(Destination.getpiece()) == piece.getcolor(piece))) //pieces are same color
            return false;
        return (    (( (Destination.x +1 == Source.x) || (Destination.x -1)  == Source.x) && ((Destination.y + 2 ==  Source.y) || Destination.y -2 == Source.y)) ||
                ( (Destination.x + 2 == Source.x) || (Destination.x - 2)  == Source.x) && ((Destination.y + 1 ==  Source.y) || Destination.y - 1 == Source.y));
    }

    private boolean PawnLogicCheck(Piece piece, Spot Source, Spot Destination, Board theboard) {

        if (Destination.isOccupied() && (Destination.getpiece().getcolor(Destination.getpiece()) == piece.getcolor(piece))) //pieces are same color
            return false;
        if ('B' == piece.getcolor(piece)) {
            if (piece.getMC() == 0) {   //Going up the board
                if (!Destination.isOccupied())
                    return ((((Destination.y - 2) == Source.y) || ((Destination.y - 1) == Source.y)) && Destination.x == Source.x && !Destination.isOccupied() && (!((theboard.getSpot(Source.x, (Source.y +1))).isOccupied())));
                else
                    return ((Destination.x - 1 == Source.x || Destination.x + 1 == Source.x) && ((Destination.y - 1) == Source.y));
            }   //above line is a diagonal attack

            else {
                if (!Destination.isOccupied())
                    return ((Destination.y - 1 == Source.y) && Destination.x == Source.x && !Destination.isOccupied());


                return ((Destination.y - 1 == Source.y) && ((Destination.x - 1 == Source.x || Destination.x + 1 == Source.x)));
            }
        }
        else //If ever 3+ colors make into switch
        {
            if (piece.getMC() == 0) {   //Going down the board
                if (!Destination.isOccupied())
                    return ((((Destination.y + 2) == Source.y) || ((Destination.y + 1) == Source.y)) && Destination.x == Source.x && !Destination.isOccupied() && (!((theboard.getSpot(Source.x, (Source.y -1))).isOccupied())));
                else
                    return ((Destination.x - 1 == Source.x || Destination.x + 1 == Source.x) && ((Destination.y + 1) == Source.y));
            }   //above line is a diagonal attack

            else {
                if (!Destination.isOccupied())
                    return ((Destination.y + 1 == Source.y) && Destination.x == Source.x);
                return ((Destination.y + 1 == Source.y) && ((Destination.x - 1 == Source.x || Destination.x + 1 == Source.x)));
        }
    }


}}
